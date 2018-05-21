package com.cclean.terminal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cclean.terminal.config.Result;
import com.cclean.terminal.constant.Constant;
import com.cclean.terminal.entity.TLinenPack;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.*;
import com.cclean.terminal.service.*;
import com.cclean.terminal.util.FastJsonUtil;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.util.InvokeUtil;
import com.cclean.terminal.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by hubin on 2018/3/23.
 * 布草相关逻辑
 */
@Service
public class LinenServiceImpl implements LinenService {

    private static Logger logger = LoggerFactory.getLogger(LinenServiceImpl.class);

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Autowired
    SkuService skuService;

    @Autowired
    HotelService hotelService;

    @Autowired
    FactoryService factoryService;

    @Autowired
    OrderService orderService;

    @Override
    public Result linens(String accessToken, RfidsVO rfidsVO) {
        Result result = Result.success();
        try {
            if (accessToken == null) return Result.paramNull();
            logger.info("accessToken value is:{}", accessToken);

            // 布草列表
            List<LinenExtend> linenExtendList = new ArrayList<>();
            String url = cloudUrl + "/linen/api/linen/statistic";
            logger.info("linens cloudUrl is:{}", url);
            String js = JSONArray.toJSONString(rfidsVO);
            JSONObject jsonParam = JSONArray.parseObject(js);
            String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
            logger.info("布草:{}", httpEntitys);
            JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                logger.error("{}{}{}", url, retCode, retInfo);
                result.setCodeInfo(retCode, retInfo);
                return result;
            }

            LinenExtend linenExtend = new LinenExtend();
            linenExtend.setLinenList(new ArrayList<>());
            // 未登记rfid列表
            List<String> unregisteredList = new ArrayList<>();
            // 已登记rifd列表
            List<String> registeredList = new ArrayList<>();
            List<String> rfids = rfidsVO.getRfids();

            JSONArray jsonArray = jsonObj.getJSONArray("data");
            if (jsonArray == null) return Result.objNull();

            for (int i = 0, j = jsonArray.size(); i < j; i++) {
                JSONObject linenJson = jsonArray.getJSONObject(i);
                if (linenJson == null) return Result.objNull();
                String id = linenJson.getString("rfidId");
                if (rfids.contains(id) && !registeredList.contains(id)) {
                    registeredList.add(id);
                }

                Linen linen = new Linen();
                linen.setId(linenJson.getString("id"));
                linen.setRfid(id);
                linen.setBatch(linenJson.getString("batch"));
                linen.setLastFactory(linenJson.getString("lastFactory"));
                linen.setLastHotel(linenJson.getString("lastHotel"));
                linen.setWashCount(linenJson.getInteger("washTime"));
                linen.setReceiveState(linenJson.getInteger("transferState"));
                linen.setLevel(linenJson.getString("level"));
                Sku sku = skuService.findSkuById(accessToken, new IdVO(linenJson.getString("skuId")));
                linen.setSku(sku);
                Property property = new Property();
                property.setId(linenJson.getString("propertyOwnerId"));
                linen.setProperty(property);
                Supplier supplier = new Supplier();
                supplier.setId(linenJson.getString("supplierId"));
                linen.setSupplier(supplier);
                linenExtend.getLinenList().add(linen);
            }
            for (String rfid : rfids) {
                if (!registeredList.contains(rfid)) {
                    unregisteredList.add(rfid);
                }
            }
            linenExtend.setUnregisteredList(unregisteredList);
            linenExtend.setRegisteredList(registeredList);
            linenExtendList.add(linenExtend);
            result.setData(linenExtendList);
            return result;
        } catch (Exception e) {
            logger.error(Constant.RET_CODE_DEBUG, e);
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getMessage());
            return result;
        }
    }


    @Override
    public Result update(String accessToken, LinenUpdateVO linenUpdateVO) {
        Result result = Result.success();
        try {
            if (accessToken == null) return Result.paramNull();
            logger.info("accessToken value is:{}", accessToken);

            StringBuffer url = new StringBuffer(cloudUrl);
            if (linenUpdateVO.getType() == 0) {
                url.append("/linen/api/linen/insert");
            } else {
                url.append("/linen/api/linen/update");
            }
            logger.info("update cloudUrl is:{}", url);

            String js = JSONArray.toJSONString(linenUpdateVO);
            JSONObject jsonParam = JSONArray.parseObject(js);
            String httpEntitys = HttpUtil.doPost(url.toString(), accessToken, jsonParam);
            logger.info("布草修改:{}", httpEntitys);
            JSONObject jsonObj = JSON.parseObject(httpEntitys);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                logger.error("{}{}{}", url, retCode, retInfo);
                result.setCodeInfo(retCode, retInfo);
                return result;
            }

            return result;
        } catch (Exception e) {
            logger.error(Constant.RET_CODE_DEBUG, e);
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getMessage());
            return result;
        }
    }

    @Override
    public Result scrap(String accessToken, LinenScrapVO linenScrapVO) {
        Result result = Result.success();
        try {
            if (accessToken == null) return Result.paramNull();
            logger.info("accessToken value is:" + accessToken);

            Linen linen = new Linen();
            String url = cloudUrl + "/linen/api/linen/scrap";
            logger.info("布草报废 cloudUrl is:{}", url);
            String js = JSONArray.toJSONString(linenScrapVO);
            JSONObject jsonParam = JSONArray.parseObject(js);
            jsonParam.put("wasteReason", jsonParam.getString("scrapReasonId"));
            jsonParam.put("responsibleType", jsonParam.getString("responsibility"));
            String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
            logger.info("布草报废 Responses content:{} ", httpEntitys);
            JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                result.setCodeInfo(retCode, retInfo);
                return result;
            }

            JSONArray jsonArray = jsonObj.getJSONArray("data");
            if (jsonArray == null) return Result.objNull();
            for (int n = 0, m = jsonArray.size(); n < m; n++) {
                JSONObject json = jsonArray.getJSONObject(n);
                if (json == null) return Result.objNull();
                linen.setId(json.getString("id"));
                linen.setRfid(json.getString("rfid"));
                linen.setBatch(json.getString("batch"));
                linen.setLastFactory(json.getString("lastFactory"));
                linen.setLastHotel(json.getString("lastHotel"));

                Sku sku = new Sku();
                sku.setId(json.getString("skuId"));
                linen.setSku(sku);
                Property property = new Property();
                property.setId(json.getString("propertyOwnerId"));
                linen.setProperty(property);
                Supplier supplier = new Supplier();
                supplier.setId(json.getString("supplierId"));
                linen.setSupplier(supplier);

                linen.setLevel(json.getString("level"));
                linen.setWashCount(json.getInteger("washTime"));
                linen.setReceiveState(json.getInteger("transferState"));
                break;
            }
            result.setData(linen);
            return result;
        } catch (Exception e) {
            logger.error(Constant.RET_CODE_DEBUG, e);
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getMessage());
            return result;
        }

    }

    @Override
    public Result dirt(String accessToken, LinenDirtVO linenDirtVO) {
        Result result = Result.success();
        try {
            if (accessToken == null) return Result.paramNull();
            logger.info("accessToken value is:{}", accessToken);

            Order order = new Order();
            String url = linenUrl + "/server/order/order/scaninfo";
            logger.info("order cloudUrl is:{}", url);
            for (int i = 0; i < linenDirtVO.getSkus().size(); i++) {
                linenDirtVO.getSkus().get(i).
                        setSkuId(linenDirtVO.getSkus().
                                get(i).getSku().getId());
            }
            String js = JSONArray.toJSONString(linenDirtVO);
            JSONObject jsonParam = JSONArray.parseObject(js);
            jsonParam.put("pointId", jsonParam.getString("deliveryPointId"));
            jsonParam.remove("deliveryPointId");
            String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
            logger.info("污物收货 Responses content: " + httpEntitys);

            JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                result.setCodeInfo(retCode, retInfo);
                return result;
            }

            JSONObject orderJson = jsonObj.getJSONObject("data");
            if (orderJson == null) return Result.objNull();
            order.setId(orderJson.getString("id"));
            order.setType(orderJson.getInteger("orderType"));
            order.setState(orderJson.getInteger("orderState"));
            order.setOperator(orderJson.getString("operator"));
            order.setCreateTime(orderJson.getDate("createTime"));
            order.setModifyTime(orderJson.getDate("modifyTime"));
            Hotel hotel = this.hotelService.findHotelById(accessToken, new IdVO(orderJson.getString("hotelId")));
            order.setHotel(hotel);
            DeliveryPoint point = this.hotelService.findPointById(accessToken, new IdVO(orderJson.getString("pointId")));
            order.setDeliveryPoint(point);

            JSONArray skuListJson = orderJson.getJSONArray("skus");
            List<SkuStatistics> list = new ArrayList<>();
            if (skuListJson != null || skuListJson.size() > 0) {
                for (int k = 0; k < skuListJson.size(); k++) {
                    JSONObject object1 = skuListJson.getJSONObject(k);
                    Sku sku = skuService.findSkuById(accessToken, new IdVO(object1.getString("skuId")));
                    Integer count = object1.getInteger("total");
                    SkuStatistics skuStatistics = new SkuStatistics();
                    skuStatistics.setSku(sku);
                    skuStatistics.setCount(count == null ? 0 : count);
                    skuStatistics.setDifferenceCount(0);
                    skuStatistics.setActualCount(0);
                    list.add(skuStatistics);
                }
            }
            order.setSkuStatisticss(list);

            result.setData(order);
            return result;
        } catch (Exception e) {
            logger.error(Constant.RET_CODE_DEBUG, e);
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getMessage());
            return result;
        }
    }

    @Override
    public Result recheck(String accessToken, LinenRecheckVO linenRecheckVO) {
        Result result = Result.success();
        try {

            if (accessToken == null) return Result.paramNull();
            logger.info("accessToken value is:{}", accessToken);

            //统计未登记布草数量
            Integer unregisteredAmout = 0;
            String url = cloudUrl + "/linen/api/linen/statistic";
            logger.info("linens cloudUrl is:{}", url);
            RfidsVO rfidsVO = new RfidsVO();
            rfidsVO.setRfids(linenRecheckVO.getRfids());
            String js = JSONArray.toJSONString(rfidsVO);
            JSONObject jsonParam = JSONArray.parseObject(js);
            String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
            logger.info("布草状态:{}", httpEntitys);
            JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                result.setCodeInfo(retCode, retInfo);
                return result;
            }

            //解析返回参数
            JSONArray linenArray = jsonObj.getJSONArray("data");
            if (linenArray == null) return Result.objNull();
            // 已登记rifd列表
            List<String> registeredList = new ArrayList<>();
            for (int i = 0, j = linenArray.size(); i < j; i++) {
                JSONObject linenJson = linenArray.getJSONObject(i);
                if (linenJson == null) return Result.objNull();
                String id = linenJson.getString("rfidId");
                if (linenRecheckVO.getRfids().contains(id) && !registeredList.contains(id)) {
                    registeredList.add(id);
                }
            }
            for (String rfid : linenRecheckVO.getRfids()) {
                if (!registeredList.contains(rfid)) {
                    unregisteredAmout++;
                }
            }


            //设置skuId
            for (int i = 0; i < linenRecheckVO.getSkuStatisticss().size(); i++) {
                linenRecheckVO.getSkuStatisticss().get(i).
                        setSkuId(linenRecheckVO.getSkuStatisticss().
                                get(i).getSku().getId());
                linenRecheckVO.getSkuStatisticss().get(i).setCount(linenRecheckVO.getSkuStatisticss().
                        get(i).getActualCount());
            }

            url = cloudUrl + "/cloud/order/order/recheck";//"http://localhost:8095/cloud/order/order/recheck";
            logger.info("order cloudUrl is:{}", url);
            js = JSONArray.toJSONString(linenRecheckVO);
            jsonParam = JSONArray.parseObject(js);
            Map<String, Integer> map = new HashMap<>();
            //查询数量
            for (String orderId : linenRecheckVO.getBasiss()) {
                Order order = orderService.info(accessToken, new IdVO(orderId));
                if (order != null) {
                    for (SkuStatistics skuStatistics : order.getSkuStatisticss()) {
                        Integer skuCount = skuStatistics.getCount();
                        if (skuStatistics.getSku() != null) {
                            if (skuStatistics.getSku().getId() != null) {
                                if (map.containsKey(skuStatistics.getSku().getId())) {
                                    map.put(skuStatistics.getSku().getId(), map.get(skuStatistics.getSku().getId()) + skuCount);
                                } else {
                                    map.put(skuStatistics.getSku().getId(), skuCount);
                                }
                            }
                        }
                    }
                }

            }
            jsonParam.put("estimateTotal", map);
            if (unregisteredAmout > 0) {
                jsonParam.put("unregisteredAmout", unregisteredAmout);
            }
            httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
            logger.info("布草复核 Responses content: " + httpEntitys);

            jsonObj = JSONObject.parseObject(httpEntitys);
            retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                result.setCodeInfo(retCode, retInfo);
                return result;
            }
            //改变布草状态
            String stateUrl= cloudUrl+"/linen/api/linen/update";
            JSONObject param = new JSONObject();
            param.put("transferState",1);
            HttpUtil.doPost(stateUrl,accessToken,param);
            return result;
        } catch (Exception e) {
            logger.error(Constant.RET_CODE_DEBUG, e);
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getMessage());
            return result;
        }
    }

    @Override
    public Result pack(String accessToken, RfidsVO rfidsVO) {
        Result result = Result.success();
        try {
            if (accessToken == null) return Result.paramNull();
            String url = cloudUrl + "/linen/api/linen/transferstate";
            JSONObject param = new JSONObject();
            param.put("transferState", 2);
            param.put("timeNum", 24);
            List<String> rfids = rfidsVO.getRfids();
            if (rfids == null) {
                throw new BusinessException("00001","请传入rfids");
            }
            Set<String> rfidset = new HashSet<>(rfids);
            param.put("rfids", rfidset);
            String json = HttpUtil.doPost(url, accessToken, param);
            logger.info("是否有已打扎的rfids:{}", json);
            JSONObject jsonObj = JSON.parseObject(json);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                result.setCodeInfo(retCode, retInfo);
                return result;
            }
            List<String> packArray = JSONObject.parseArray(jsonObj.getString("data"), String.class);
            if (packArray != null && packArray.size() > 0) {
                result.setCodeInfo(Constant.RET_CODE_DEBUG, "有布草已打扎");
                return result;
            }


            url = cloudUrl + "/linen/api/sku/statistic";
            json = HttpUtil.doPost(url, accessToken, param);
            logger.info("打扎的布草信息:{}", json);
            jsonObj = JSON.parseObject(json);
            retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                result.setCodeInfo(retCode, retInfo);
                return result;
            }

            SkuStatistics skuStatistics = new SkuStatistics();
            JSONArray jsonArray = jsonObj.getJSONArray("data");
            if (jsonArray == null) return Result.objNull();
            if (jsonArray.size() != 1) {
                result.setCodeInfo("00108", "sku信息不一致");
                return result;
            }

            JSONObject object = jsonArray.getJSONObject(0);
            skuStatistics.setCount(object.getInteger("count"));
            IdVO idVO = new IdVO();
            String skuId = object.getString("skuId");
            idVO.setId(skuId);
            Sku sku = skuService.findSkuById(accessToken, idVO);
            if (sku == null || skuStatistics.getCount() != sku.getPackCnt()) {
                result.setCodeInfo("00109", "捆扎数不一致");
                return result;
            }

            TLinenPack tLinenPack = new TLinenPack();
            tLinenPack.setSkuId(skuId);
            tLinenPack.setCount(skuStatistics.getCount());
            tLinenPack.setRfids(new ArrayList<>(rfidset));
            String str = JSONArray.toJSONString(tLinenPack);
            JSONObject jsonParam = JSONArray.parseObject(str);

            url = cloudUrl + "/linen/api/linen/linenpack";
            logger.info("linenPack cloudUrl is:" + url);
            json = HttpUtil.doPostStr(url, accessToken, jsonParam.toJSONString());
            logger.info("打扎:{}", json);
            Type type = new TypeReference<ResultData<TLinenPack>>() {
            }.getType();
            ResultData<TLinenPack> linenPackDate = FastJsonUtil.toObject(json, type);
            retCode = linenPackDate.getRetCode();
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = linenPackDate.getRetInfo();
                result.setCodeInfo(retCode, retInfo);
                return result;
            }
            //布草打扎信息
            tLinenPack = linenPackDate.getData();
            LinenPack linenPack = new LinenPack();
            linenPack.setRfids(tLinenPack.getRfids());
            linenPack.setCount(tLinenPack.getCount());
            linenPack.setCode(tLinenPack.getCode());
            linenPack.setCreateTime(new Date());
            linenPack.setCreator(tLinenPack.getCreator());
            linenPack.setSku(sku);
            result.setData(linenPack);
            return result;
        } catch (Exception e) {
            logger.error(Constant.RET_CODE_DEBUG, e);
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getLocalizedMessage());
            return result;
        }
    }

    @Override
    public Result types(String accessToken) {
        Result result = Result.success();
        try {
            if (accessToken == null) return Result.paramNull();
            logger.info("accessToken value is:{}", accessToken);

            //布草类别列表
            List<LinenType> linenTypeList = new ArrayList<>();
            String url = linenUrl + "/cloud/manage/v1/dictionary/page";
            logger.info("linenType cloudUrl is:" + url);
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("type", "skuType");
            String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
            logger.info("布草类别列表 Responses content: {}", httpEntitys);

            JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                result.setCodeInfo(retCode, retInfo);
                return result;
            }

            JSONObject listObj = jsonObj.getJSONObject("list");
            if (listObj == null) return Result.objNull();
            JSONArray typesJson = listObj.getJSONArray("list");
            if (typesJson == null) return Result.objNull();

            if (typesJson != null && typesJson.size() > 0) {
                for (int n = 0, m = typesJson.size(); n < m; n++) {
                    JSONObject typeJson = typesJson.getJSONObject(n);
                    if (typeJson == null) return Result.objNull();
                    LinenType linenType = new LinenType();
                    linenType.setId(typeJson.getString("id"));
                    linenType.setName(typeJson.getString("value"));
                    linenTypeList.add(linenType);
                }
            }

            result.setData(linenTypeList);
            return result;
        } catch (Exception e) {
            logger.error(Constant.RET_CODE_DEBUG, e);
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getMessage());
            return result;
        }
    }

    /**
     * 根据rfids 查询 打扎单信息
     *
     * @param token
     * @param rfids
     * @return
     */
    @Override
    public LinenPack findPacksByRfids(String token, List<String> rfids) throws BusinessException {
        String url = cloudUrl + "/linen/api/linen/packinfo/byrfid";
        JSONObject param = new JSONObject();
        Set<String> rfidset = new HashSet<>(rfids);
        param.put("rfids", rfidset);
        String datajson = InvokeUtil.invokeString(url, token, param);
        List<String> list = JSONArray.parseArray(datajson, String.class);
        if (list == null || list.size() == 0) {
            throw new BusinessException("00001", "未查询到打扎单");
        }
        Collections.sort(list);
        String packurl = cloudUrl + "/linen/api/linen/packinfo";
        param.clear();
        param.put("id", list.get(list.size()-1));
        JSONObject object = InvokeUtil.invokeResult(packurl, token, param);
        LinenPack pack = FastJsonUtil.toObject(object.toJSONString(), LinenPack.class);
        List<String> array = JSONArray.parseArray(object.getString("rfids"), String.class);
        if (rfidset.size() != array.size()) {
            logger.error("根据rfids查询打扎单：未查询到打扎单：传入数量与打扎数量不符");
            throw new BusinessException("00001", "未查询到打扎单");
        }
        if (!rfidset.containsAll(array) || !array.containsAll(rfidset)) {
            logger.error("根据rfids查询打扎单：未查询到打扎单：传入布草与打扎单布草信息不符");
            throw new BusinessException("00001", "未查询到打扎单");
        }
        String skuurl = linenUrl+"/cloud/manage/v1/sku/info";
        param.clear();
        param.put("id",object.getString("skuId"));
        String httpEntys = HttpUtil.doPost(skuurl, token, param);
        JSONObject jsonObj = JSONObject.parseObject(httpEntys);
        String retCode = jsonObj.getString("retCode");
        if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
            String retInfo = jsonObj.getString("retInfo");
            throw new BusinessException(retCode, retInfo);
        }
        Sku sku = FastJsonUtil.toObject(jsonObj.getString("sku"), Sku.class);
        pack.setSku(sku);
        return pack;
    }

    /**
     * 根据rfids查询sku的信息
     *
     * @param token
     * @param rfids
     * @return
     * @throws BusinessException
     */
    @Override
    public List<SkuInfoEntity> findSkuByRfid(String token, List<String> rfids) throws BusinessException {
        String url = cloudUrl + "/linen/api/sku/statistic";
        JSONObject param = new JSONObject();
        param.put("rfids", rfids);
        String datajson = InvokeUtil.invokeString(url, token, param);
        List<SkuInfoEntity> list = new ArrayList<>();
        List<JSONObject> array = JSONObject.parseArray(datajson, JSONObject.class);
        if (array == null || array.size() == 0) {
            return list;
        }
        Set<String> skuids = new HashSet<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.get(i);
            skuids.add(object.getString("skuId"));
        }
        if (skuids.size() == 0) {
            return list;
        }
        for (String skuid : skuids) {
            String skuurl = linenUrl + "/cloud/manage/v1/sku/info";
            param.put("id", skuid);
            String httpEntys = HttpUtil.doPost(skuurl, token, param);
            JSONObject jsonObj = JSONObject.parseObject(httpEntys);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                throw new BusinessException(retCode, retInfo);
            }
            SkuInfoEntity skuInfo = FastJsonUtil.toObject(jsonObj.getString("sku"), SkuInfoEntity.class);
            list.add(skuInfo);
        }
        return list;

    }
}
