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
import com.cclean.terminal.util.StringUtils;
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

    @Value("${UNKNOWSKU}")
    private String UNKNOWSKU;

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
                Sku sku = skuService.findSkuById(accessToken, linenJson.getString("skuId"));
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
            List<String> rfids = linenUpdateVO.getRfids();
            if (rfids == null || rfids.size() == 0) {
                throw new BusinessException("00001", "请传入rfids");
            }
            Set<String> set = new HashSet<>(rfids);
            linenUpdateVO.setRfids(new ArrayList<>(set));
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
            String responsibility = linenScrapVO.getResponsibility();
            if (StringUtils.isHasEmpty(responsibility, linenScrapVO.getScrapReasonId())) {
                throw new BusinessException("00001", "请填写报废的责任方和原因");
            }
            if ("0".equals(responsibility) || "1".equals(responsibility) || "2".equals(responsibility)) {
                if (StringUtils.isBlank(linenScrapVO.getScrapReasonId())) {
                    throw new BusinessException("00001", "请指明责任方单位");
                }
            }
            Linen linen = new Linen();
            String url = cloudUrl + "/linen/api/linen/scrap";
            logger.info("布草报废 cloudUrl is:{}", url);
            String js = JSONArray.toJSONString(linenScrapVO);
            JSONObject jsonParam = JSONArray.parseObject(js);
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

    /**
     * 布草收脏
     *
     * @param accessToken
     * @param linenDirtVO
     * @return
     */
    @Override
    public Result dirt(String accessToken, LinenDirtVO linenDirtVO) {
        Result result = Result.success();
        try {
            List<String> rfids = linenDirtVO.getRfids();
            if (rfids == null || rfids.size() == 0) {
                throw new BusinessException("00001", "请传入rfids");
            }
            HashSet set = new HashSet(rfids);
            linenDirtVO.setRfids(new ArrayList<>(set));
            Order order = new Order();
            String url = linenUrl + "/server/order/order/scaninfo";
            logger.info("order cloudUrl is:{}", url);
            List<SkuStatisticsCopy> skus = linenDirtVO.getSkus();
            for (int i = 0; i < skus.size(); i++) {
                String id = skus.get(i).getSku().getId();
                skus.get(i).setSkuId(id);
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
                    Sku sku = skuService.findSkuById(accessToken, object1.getString("skuId"));
                    Integer count = object1.getInteger("total");
                    SkuStatistics skuStatistics = new SkuStatistics();
                    skuStatistics.setSku(sku);
                    skuStatistics.setCount(count == null ? 0 : count);
                    skuStatistics.setDifferenceCount(0);
                    skuStatistics.setActualCount(0);
                    list.add(skuStatistics);
                }
                Collections.sort(list, Comparator.comparing(skutics -> skutics.getSku().getName()));
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

    /**
     * 脏布草复核
     *
     * @param token
     * @param ids
     * @param rfids
     * @return
     * @throws BusinessException
     */
    @Override
    public Result recheck(String token, Set<String> ids, Set<String> rfids) throws BusinessException {

        String url = cloudUrl + "/linen/api/linen/alreadyinsert";
        JSONObject param = new JSONObject();
        param.put("rfids", rfids);
        String data = InvokeUtil.invokeString(url, token, param);
        //已登记rfids
        List<String> list = JSONArray.parseArray(data, String.class);
        //统计sku数量
        url = cloudUrl + "/linen/api/sku/statistic";
        param.put("rfids", list);
        String datajson = InvokeUtil.invokeString(url, token, param);
        List<JSONObject> array = JSONArray.parseArray(datajson, JSONObject.class);
        //查询订单的数量
        url = cloudUrl + "/cloud/order/scaninfo/list";
        param.clear();
        param.put("ids", ids);
        String dataorder = InvokeUtil.invokeString(url, token, param);
        List<JSONObject> orders = JSONArray.parseArray(dataorder, JSONObject.class);
        if (orders.size() != ids.size()) {
            throw new BusinessException("00001", "传入收脏单有误，未查询到相关收脏单");
        }
        Map<String, Integer> skucount = new HashMap<>();
        if (orders != null && orders.size() > 0) {
            for (int i = 0; i < orders.size(); i++) {
                JSONObject order = orders.get(i);
                JSONArray skus = order.getJSONArray("skus");
                if (skus != null && skus.size() > 0) {
                    for (int j = 0; j < skus.size(); j++) {
                        JSONObject sku = skus.getJSONObject(j);
                        String skuId = sku.getString("skuId");
                        Integer total = sku.getInteger("total");
                        if (skucount.containsKey(skuId)) {
                            Integer count = skucount.get(skuId);
                            skucount.put(skuId, count + total);
                        } else {
                            skucount.put(skuId, total);
                        }
                    }
                }
            }
        }
        List<Map<String, Object>> skus = new ArrayList<>();
        Set<String> lsset = new HashSet<>();
        if (array != null && array.size() > 0) {
            for (int i = 0; i < array.size(); i++) {
                Map<String, Object> sku = new HashMap<>();
                JSONObject object = array.get(i);
                String skuId = object.getString("skuId");
                lsset.add(skuId);
                sku.put("skuId", skuId);
                int count = object.getIntValue("count");
                if (skucount.containsKey(skuId)) {
                    Integer count1 = skucount.get(skuId);
                    sku.put("total", count);
                    sku.put("estimateTotal", count1);
                } else {
                    sku.put("total", count);
                    sku.put("estimateTotal", 0);
                }
                skus.add(sku);
            }
        }
        Set<String> set = skucount.keySet();
        set.removeAll(lsset);
        for (String skuId : set) {
            Map<String, Object> sku = new HashMap<>();
            sku.put("skuId", skuId);
            sku.put("total", 0);
            sku.put("estimateTotal", skucount.get(skuId));
            skus.add(sku);
        }
        //未登记的rfids
        List<String> unregist = new ArrayList<>(rfids);
        unregist.removeAll(list);
        if (unregist.size() > 0) {
            Map<String, Object> sku = new HashMap<>();
            sku.put("skuId", UNKNOWSKU);
            sku.put("total", unregist.size());
            sku.put("estimateTotal", 0);
            skus.add(sku);
        }
        //基础服务
        url = cloudUrl + "/cloud/order/scaninfo/recheck";
        param.clear();
        param.put("scanIds", ids);
        param.put("rfids", rfids);
        param.put("skus", skus);          //rfids统计sku数量
        String str = InvokeUtil.invokeString(url, token, param);
        //变更rfids的状态
        String stateUrl = cloudUrl + "/linen/api/linen/update";
        param.clear();
        param.put("transferState", 4);
        param.put("rfids", list);
        HttpUtil.doPost(stateUrl, token, param);
        return new Result("00000", "操作成功");
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
                throw new BusinessException("00001", "请传入rfids");
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
            String skuId = object.getString("skuId");
            Sku sku = skuService.findSkuById(accessToken, skuId);
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
        param.put("id", list.get(list.size() - 1));
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
        String skuurl = linenUrl + "/cloud/manage/v1/sku/info";
        param.clear();
        param.put("id", object.getString("skuId"));
        String httpEntys = HttpUtil.doPost(skuurl, token, param);
        JSONObject jsonObj = JSONObject.parseObject(httpEntys);
        String retCode = jsonObj.getString("retCode");
        if (!"00000".equals(retCode)) {
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
            if (!"00000".equals(retCode)) {
                String retInfo = jsonObj.getString("retInfo");
                throw new BusinessException(retCode, retInfo);
            }
            SkuInfoEntity skuInfo = FastJsonUtil.toObject(jsonObj.getString("sku"), SkuInfoEntity.class);
            list.add(skuInfo);
        }
        return list;

    }
}
