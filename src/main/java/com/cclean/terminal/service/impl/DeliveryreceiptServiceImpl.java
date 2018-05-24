package com.cclean.terminal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.*;
import com.cclean.terminal.service.DeliveryreceiptService;
import com.cclean.terminal.service.HotelService;
import com.cclean.terminal.service.SkuService;
import com.cclean.terminal.service.UserService;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.util.InvokeUtil;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.vo.GenerateVO;
import com.cclean.terminal.vo.IdVO;
import com.cclean.terminal.vo.OrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by hubin on 2018/3/23.
 * 配送单相关逻辑
 */
@Service
public class DeliveryreceiptServiceImpl implements DeliveryreceiptService {

    private static Logger logger = LoggerFactory.getLogger(DeliveryreceiptServiceImpl.class);


    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Value("${invoke.deliveryorder.page}")
    private String deliveryorderPageUrl;

    @Value("${invoke.deliveryorder.info}")
    private String deliveryorderInfoUrl;

    @Resource
    private HotelService hotelService;

    @Resource
    private SkuService skuService;

    @Resource
    private UserService userService;


    /**
     * 任务单生成配送单
     *
     * @param accessToken
     * @param generateVO
     * @return
     * @throws BusinessException
     */
    @Override
    public DeliveryReceipt generate(String accessToken, GenerateVO generateVO) throws BusinessException {
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt();
        String url = linenUrl + "/server/order/deliveryorder/generate";
        for (int i = 0; i < generateVO.getSkuStatisticss().size(); i++) {
            generateVO.getSkuStatisticss().get(i).
                    setSkuId(generateVO.getSkuStatisticss().
                            get(i).getSku().getId());
        }
        String js = JSONArray.toJSONString(generateVO);
        JSONObject jsonParam = JSONArray.parseObject(js);
        jsonParam.put("skus", jsonParam.getJSONArray("skuStatisticss"));
        jsonParam.put("pickOrderId", jsonParam.getString("workOrderId"));
        jsonParam.remove("skuStatisticss");
        jsonParam.remove("workOrderId");
        String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
        logger.info("生成配送单信息：{}", httpEntitys);
        JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObj.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObj.getString("retInfo"));
        }
        JSONObject dataJson = jsonObj.getJSONObject("data");
        if (dataJson == null) {
            return deliveryReceipt;
        }

        deliveryReceipt.setId(dataJson.getString("id"));
        deliveryReceipt.setState(dataJson.getInteger("state") == 50 ? 1 : dataJson.getInteger("state"));
        String uid = dataJson.getString("operator");
        deliveryReceipt.setOperator(uid);
        deliveryReceipt.setDeliveryTime(dataJson.getDate("takeTime"));
        deliveryReceipt.setCreateTime(dataJson.getDate("createTime"));
        deliveryReceipt.setModifyTime(dataJson.getDate("modifyTime"));
        Set<String> uids = new HashSet<>();
        uids.add(uid);
        Map<String, String> users = this.userService.findUsersByIds(uids, accessToken);
        deliveryReceipt.setOperatorName(users.get(uid));
        Hotel hotel = this.hotelService.findHotelById(accessToken, new IdVO(dataJson.getString("hotelId")));
        deliveryReceipt.setHotel(hotel);
        DeliveryPoint point = this.hotelService.findPointById(accessToken, new IdVO(dataJson.getString("pointId")));
        deliveryReceipt.setDeliveryPoint(point);
        JSONArray skuListJson = dataJson.getJSONArray("skus");
        Map<String, Object> map = this.skuService.stringToBean(skuListJson, accessToken);
        List<SkuStatistics> list = (List<SkuStatistics>) map.get("skuStatisticsList");
        Collections.sort(list, Comparator.comparing(o -> o.getSku().getName()));
        deliveryReceipt.setSkuStatisticss(list);
        deliveryReceipt.setSkuStatisTotal((Integer) map.get("total"));
        return deliveryReceipt;
    }

    /**
     * 配送单详情
     *
     * @param accessToken 授权码
     * @param idVO        参数（id）
     * @return
     * @throws BusinessException
     */
    @Override
    public DeliveryReceipt info(String accessToken, IdVO idVO) throws BusinessException {
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt();
        String url = cloudUrl + deliveryorderInfoUrl;
        String js = JSONArray.toJSONString(idVO);
        JSONObject jsonParam = JSONArray.parseObject(js);
        String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
        logger.info("配送单详情：{}" + httpEntitys);
        JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObj.getString("retCode");
        if (!"00000".equals(retCode)) {
            String retInfo = jsonObj.getString("retInfo");
            throw new BusinessException(retCode, retInfo);
        }
        JSONObject dataJson = jsonObj.getJSONObject("data");
        if (dataJson == null) {
            return deliveryReceipt;
        }
        deliveryReceipt.setId(dataJson.getString("id"));
        deliveryReceipt.setState(dataJson.getInteger("state") == 50 ? 1 : dataJson.getInteger("state"));
        String uid = dataJson.getString("operator");
        deliveryReceipt.setOperator(uid);
        deliveryReceipt.setDeliveryTime(dataJson.getDate("takeTime"));
        deliveryReceipt.setCreateTime(dataJson.getDate("createTime"));
        deliveryReceipt.setModifyTime(dataJson.getDate("modifyTime"));
        Set<String> uids = new HashSet<>();
        uids.add(uid);
        Map<String, String> users = this.userService.findUsersByIds(uids, accessToken);
        deliveryReceipt.setOperatorName(users.get(uid));
        Hotel hotel = this.hotelService.findHotelById(accessToken, new IdVO(dataJson.getString("hotelId")));
        deliveryReceipt.setHotel(hotel);
        DeliveryPoint point = this.hotelService.findPointById(accessToken, new IdVO(dataJson.getString("pointId")));
        deliveryReceipt.setDeliveryPoint(point);
        JSONArray skuListJson = dataJson.getJSONArray("skus");
        Map<String, Object> map = this.skuService.stringToBean(skuListJson, accessToken);
        List<SkuStatistics> list = (List<SkuStatistics>) map.get("skuStatisticsList");
        Collections.sort(list, Comparator.comparing(o -> o.getSku().getName()));
        deliveryReceipt.setSkuStatisticss(list);
        deliveryReceipt.setSkuStatisTotal((Integer) map.get("total"));
        return deliveryReceipt;
    }

    /**
     * 查询配送单列表
     *
     * @param token
     * @param orderVO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<DeliveryReceipt> list(String token, OrderVO orderVO) throws BusinessException {
        List<DeliveryReceipt> list = new ArrayList<>();
        String url = cloudUrl + deliveryorderPageUrl;
        JSONObject param = InvokeUtil.jsonParam(orderVO, "");
        JSONObject data = InvokeUtil.invokeResult(url, token, param);
        logger.info("配送单列表：{}", data);
        JSONArray jsonArray = data.getJSONArray("list");
        if (jsonArray == null || jsonArray.size() == 0) {
            return list;
        }
        Set<String> hotelSet = new HashSet<>();
        Set<String> pointSet = new HashSet<>();
        Set<String> skuSet = new HashSet<>();
        Set<String> userSet = new HashSet<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            hotelSet.add(obj.getString("hotelId"));
            pointSet.add(obj.getString("pointId"));
            userSet.add(obj.getString("operator"));
            JSONArray skus = obj.getJSONArray("skus");
            if (skus != null && skus.size() > 0) {
                for (int j = 0; j < skus.size(); j++) {
                    JSONObject objsku = skus.getJSONObject(j);
                    skuSet.add(objsku.getString("skuId"));
                }
            }
        }
        Map<String, Hotel> hotels = this.hotelService.findHotelsByIds(hotelSet);
        Map<String, DeliveryPoint> points = this.hotelService.findPointsByIds(pointSet);
        Map<String, String> users = this.userService.findUsersByIds(userSet, token);
        Map<String, Sku> skus = this.skuService.findSkusByIds(skuSet);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            DeliveryReceipt deliveryReceipt = new DeliveryReceipt();
            deliveryReceipt.setId(obj.getString("id"));
            deliveryReceipt.setHotel(hotels.get(obj.getString("hotelId")));
            deliveryReceipt.setDeliveryPoint(points.get(obj.getString("pointId")));
            deliveryReceipt.setState(obj.getInteger("state") == 50 ? 1 : obj.getInteger("state"));
            deliveryReceipt.setOperator(obj.getString("operator"));
            deliveryReceipt.setOperatorName(users.get(obj.getString("operator")));
            deliveryReceipt.setDeliveryTime(obj.getDate("takeTime"));
            deliveryReceipt.setCreateTime(obj.getDate("createTime"));
            deliveryReceipt.setModifyTime(obj.getDate("modifyTime"));
            int total = 0;
            JSONArray objskus = obj.getJSONArray("skus");
            List<SkuStatistics> skutics = new ArrayList<>();
            if (objskus != null && objskus.size() > 0) {
                for (int j = 0; j < objskus.size(); j++) {
                    JSONObject skujson = objskus.getJSONObject(j);
                    SkuStatistics skusta = new SkuStatistics();
                    String id = skujson.getString("skuId");
                    skusta.setSku(skus.get(id));
                    int count = StringUtils.toInt(skujson.getString("deliveryCount"));
                    total += count;
                    skusta.setCount(count);
                    skusta.setActualCount(skujson.getInteger("scanCount"));
                    skutics.add(skusta);
                }
            }
            Collections.sort(skutics, Comparator.comparing(o -> o.getSku().getName()));
            deliveryReceipt.setSkuStatisTotal(total);
            deliveryReceipt.setSkuStatisticss(skutics);
            list.add(deliveryReceipt);
        }
        return list;


    }
}
