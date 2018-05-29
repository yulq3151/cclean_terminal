package com.cclean.terminal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.config.Result;
import com.cclean.terminal.constant.Constant;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.Sku;
import com.cclean.terminal.model.SkuExtend;
import com.cclean.terminal.model.SkuStatistics;
import com.cclean.terminal.service.SkuService;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.util.InvokeUtil;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.vo.IdVO;
import com.cclean.terminal.vo.PageVO;
import com.cclean.terminal.vo.RfidsVO;
import com.cclean.terminal.vo.SkuVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Sku相关逻辑
 */
@Service
public class SkuServiceImpl implements SkuService {

    private static Logger logger = LoggerFactory.getLogger(SkuServiceImpl.class);

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Value("${invoke.sku.info}")
    private String skuInfoInvokeUrl;

    @Value("${invoke.skus.list}")
    private String skuslisturl;

    @Override
    public Result statistics(String accessToken, RfidsVO rfidsVO) throws BusinessException {
        Result result = Result.success();
        try {
            if (accessToken == null) return Result.paramNull();
            logger.info("accessToken value is:{}", accessToken);

            SkuExtend skuExtend = new SkuExtend();
            List<String> rfids = rfidsVO.getRfids();
            String url = cloudUrl + "/linen/api/linen/statistic";
            logger.info("linens cloudUrl is:{}", url);
            String js = JSONArray.toJSONString(rfidsVO);
            JSONObject jsonParam = JSONArray.parseObject(js);
            String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
            logger.info("已登记的的sku：respose:{}", httpEntitys);
            JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                logger.error("url:{},retCode:{},retInfo:{}", url, retCode, retInfo);
                result.setCodeInfo(retCode, retInfo);
                return result;
            }

            // Sku统计信息列表
            List<SkuStatistics> skuStatisticsList = new ArrayList<>();
            // 未登记rfid列表
            List<String> unregisteredList = new ArrayList<>();
            // 已登记rifd列表
            List<String> registeredList = new ArrayList<>();

            JSONArray linenArray = jsonObj.getJSONArray("data");
            if (linenArray == null) return Result.objNull();

            for (int i = 0, j = linenArray.size(); i < j; i++) {
                JSONObject linenJson = linenArray.getJSONObject(i);
                if (linenJson == null) return Result.objNull();
                String id = linenJson.getString("rfidId");
                if (rfids.contains(id) && !registeredList.contains(id)) {
                    registeredList.add(id);
                }
            }
            for (String rfid : rfids) {
                if (!registeredList.contains(rfid)) {
                    unregisteredList.add(rfid);
                }
            }

            skuExtend.setUnregisteredList(unregisteredList);
            skuExtend.setRegisteredList(registeredList);
            url = cloudUrl + "/linen/api/sku/statistic";
            logger.info("linens cloudUrl is:{}", url);
            js = JSONArray.toJSONString(rfidsVO);
            jsonParam = JSONArray.parseObject(js);
            httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
            logger.info("根据rfids查询sku：respose:{}", httpEntitys);
            jsonObj = JSONObject.parseObject(httpEntitys);
            retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                logger.error("{}{}{}", url, retCode, retInfo);
                result.setCodeInfo(retCode, retInfo);
                return result;
            }

            JSONArray skuArray = jsonObj.getJSONArray("data");
            if (skuArray == null) return Result.objNull();

            for (int n = 0, m = skuArray.size(); n < m; n++) {
                JSONObject skuJson = skuArray.getJSONObject(n);
                SkuStatistics skuStatistics = new SkuStatistics();
                skuStatistics.setCount(skuJson.getInteger("count"));
                IdVO idVO = new IdVO();
                idVO.setId(skuJson.getString("skuId"));
                Sku sku = findSkuById(accessToken, idVO);
                skuStatistics.setSku(sku);
                skuStatisticsList.add(skuStatistics);
            }
            skuExtend.setSkuStatisticsList(skuStatisticsList);

            //获取8个小时内已收脏数量
            url = cloudUrl + "/linen/api/linen/transferstate";
            logger.info("linens cloudUrl is:{}", url);
            js = JSONArray.toJSONString(rfidsVO);
            jsonParam = JSONArray.parseObject(js);
            jsonParam.put("transferState", "1");
            jsonParam.put("timeNum", "8");
            httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
            logger.info("查询布草状态：respose:{}", httpEntitys);
            jsonObj = JSONObject.parseObject(httpEntitys);
            retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                logger.error("{}{}{}", url, retCode, retInfo);
                result.setCodeInfo(retCode, retInfo);
                return result;
            }
            List<String> list = JSONArray.parseArray(jsonObj.getString("data"), String.class);
            if (list == null) return Result.objNull();
            skuExtend.setRecevicedList(list);
            skuExtend.setReceivedCount(list.size());
            result.setData(skuExtend);
            return result;
        } catch (Exception e) {
            logger.error(Constant.RET_CODE_DEBUG, e);
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getMessage());
            return result;
        }
    }

    /**
     * 查询sku列表
     *
     * @param accessToken
     * @param skuVO
     * @return
     * @throws BusinessException
     */

    @Override
    public Result page(String accessToken, SkuVO skuVO) {
        Result result = Result.success();
        try {
            if (skuVO.getPageNum() == null && skuVO.getPageSize() == null) {
                result.setCodeInfo("00100", "分页参数必填");
            }
            if (accessToken == null) return Result.paramNull();

            String url = linenUrl + "/cloud/manage/v1/sku/page";
            String js = JSONArray.toJSONString(skuVO);
            JSONObject jsonParam = JSONArray.parseObject(js);
            jsonParam.put("name", jsonParam.getString("code"));
            if (jsonParam.getString("codeType") != null) {
                jsonParam.put("skuType", jsonParam.getString("codeType"));
            }
            jsonParam.remove("code");
            jsonParam.remove("codeType");
            String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
            logger.info("sku列表：respose:{}", httpEntitys);
            JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                result.setCodeInfo(retCode, "/cloud/manage/v1/sku/page:" + retInfo);
                return result;
            }

            List<Sku> skuList = new ArrayList<>();
            JSONObject dataJson = jsonObj.getJSONObject("page");
            if (dataJson == null) return Result.objNull();
            JSONArray skuArrayJson = dataJson.getJSONArray("list");
            if (skuArrayJson == null) return Result.objNull();

            for (int n = 0, m = skuArrayJson.size(); n < m; n++) {
                JSONObject skuJson = skuArrayJson.getJSONObject(n);
                if (skuJson == null) return Result.objNull();
                Sku sku = new Sku();
                sku.setId(skuJson.getString("id"));
                sku.setCode(skuJson.getString("code"));
                sku.setBriefCode(skuJson.getString("briefCode"));
                sku.setName(skuJson.getString("name"));
                sku.setSize(skuJson.getString("size"));
                sku.setType(skuJson.getString("type"));
                skuList.add(sku);
            }

            PageVO pageVO = new PageVO();
            pageVO.setPageSize(skuVO.getPageSize());
            pageVO.setPageNum(skuVO.getPageNum());
            PageMo<Sku> page = new PageMo<>(skuList, pageVO, dataJson.getInteger("total"));
            result.setData(page);
            return result;
        } catch (Exception e) {
            logger.error(Constant.RET_CODE_DEBUG, e);
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getMessage());
            return result;
        }
    }

    /**
     * 查询sku详情
     *
     * @param accessToken 授权码
     * @param idVO        参数（id）
     * @return
     * @throws BusinessException
     */
    @Override
    public Sku findSkuById(String accessToken, IdVO idVO) throws BusinessException {

        String url = linenUrl + skuInfoInvokeUrl;
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("id", idVO.getId());
        String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
        logger.info("根据id查询sku：respose:{}", httpEntitys);
        JSONObject jsonResult = JSONObject.parseObject(httpEntitys);
        String retCode = jsonResult.getString("retCode");
        if (StringUtils.isNotEquals("00000", retCode)) {
            if (retCode.equals("00102")) {
                return null;
            } else {
                throw new BusinessException(retCode, jsonResult.getString("retInfo"));
            }
        }
        JSONObject dataJson = jsonResult.getJSONObject("sku");
        if (dataJson == null) {
            return null;
        }
        Sku sku = new Sku();
        sku.setId(dataJson.getString("id"));
        sku.setCode(dataJson.getString("code"));
        sku.setBriefCode(dataJson.getString("briefCode"));
        sku.setName(dataJson.getString("name"));
        sku.setSize(dataJson.getString("sizeValue"));
        sku.setPackCnt(dataJson.getInteger("packCnt"));
        return sku;
    }

    @Override
    public Map<String, Object> stringToBean(JSONArray skuListJson, String accessToken) throws BusinessException {
        Map<String, Object> map = new HashMap<>();
        List<SkuStatistics> skuStatisticsList = new ArrayList<>();
        Integer total = 0;
        if (skuListJson instanceof JSONArray) {
            if (skuListJson != null && skuListJson.size() > 0) {
                for (int n = 0, m = skuListJson.size(); n < m; n++) {
                    JSONObject skuJson = skuListJson.getJSONObject(n);
                    Sku sku = this.findSkuById(accessToken, new IdVO(skuJson.getString("skuId")));
                    SkuStatistics statistics = new SkuStatistics();
                    statistics.setSku(sku);

                    Integer count = skuJson.getInteger("total");
                    if (count == null) {
                        count = skuJson.getInteger("expectCount");
                    }
                    if (count == null) {
                        count = skuJson.getInteger("deliveryCount");
                    }
                    if (count == null) {
                        total = 0;
                    } else {
                        total += count;
                    }
                    statistics.setCount(count);

                    Integer normalCount = skuJson.getInteger("normalCount");
                    if (normalCount == null) {
                        normalCount = skuJson.getInteger("realCount");
                    }
                    if (normalCount == null) {
                        normalCount = skuJson.getInteger("sacnCount");
                    }
                    if (normalCount == null) {
                        statistics.setActualCount(0);
                    } else {
                        statistics.setActualCount(normalCount);
                    }

                    Integer rewashCount = skuJson.getInteger("rewashCount");
                    if (rewashCount == null) {
                        rewashCount = skuJson.getInteger("diffCount");
                    }
                    if (rewashCount == null) {
                        statistics.setDifferenceCount(0);
                    } else {
                        statistics.setDifferenceCount(rewashCount);
                    }
                    skuStatisticsList.add(statistics);
                }

            }
        }
        map.put("skuStatisticsList", skuStatisticsList);
        map.put("total", total);
        return map;
    }

    /**
     * 根据sku ID查询，返回map对象，key是sku ID，value是sku本身
     *
     * @param ids
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, Sku> findSkusByIds(Set<String> ids) throws BusinessException {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }
        String url = cloudUrl + skuslisturl;
        JSONObject param = new JSONObject();
        param.put("ids", ids);
        String httpEntitys = HttpUtil.doPost(url, "", param);
        logger.info("sku列表：respose:{}", httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        List<Sku> skuList = JSONObject.parseArray(jsonObject1.getString("data"), Sku.class);
        Map<String, Sku> map = new HashMap<>();
        for (int i = 0; i < skuList.size(); i++) {
            Sku sku = skuList.get(i);
            map.put(sku.getId(), sku);
        }
        return map;
    }




    /**
     * 根据rfids 查询每个布草的信息
     *
     * @param token
     * @param rfids
     * @param scanTime
     * @return  rfid,skuid,skuname,skusize
     */
    @Override
    public List<Map<String, String>> findSkuByRfid(String token, List<String> rfids, String scanTime) throws BusinessException {
        List<Map<String, String>> list = new ArrayList<>();
        if (rfids == null || rfids.size() == 0) {
            return list;
        }
        Set<String> set = new HashSet<>(rfids);
        String url = cloudUrl + "/linen/api/linen/statistic";
        JSONObject param = new JSONObject();
        param.put("rfids", set);
        String data = InvokeUtil.invokeString(url, token, param);
        List<JSONObject> array = JSONArray.parseArray(data, JSONObject.class);
        if (array == null || array.size() == 0) {
            return list;
        }
        Set<String> skuids = new HashSet<>();
        Set<String> rfides = new HashSet<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.get(i);
            String skuId = object.getString("skuId");
            String rfidId = object.getString("rfidId");
            String transferState = object.getString("transferState");
            skuids.add(skuId);
            rfides.add(rfidId);
            Map<String, String> map = new HashMap<>();
            map.put("rfid", rfidId);
            map.put("scanTime",scanTime);
            map.put("status",transferState);
            map.put("skuId", skuId);
            list.add(map);
        }
        if (skuids.size() > 0) {
            Map<String, Sku> skus = this.findSkusByIds(skuids);
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map = list.get(i);
                String skuId = map.get("skuId");
                Sku sku = skus.get(skuId);
                map.put("skuName", sku.getName());
                map.put("skuCode",sku.getCode());
            }
        }
        //去除已登记的
        set.removeAll(rfides);
        if (set.size() > 0) {
            for (String rfid : set) {
                Map<String,String> map = new HashMap<>();
                map.put("rfid",rfid);
                map.put("status","-1");
                list.add(map);
            }
        }
        return list;
    }
}
