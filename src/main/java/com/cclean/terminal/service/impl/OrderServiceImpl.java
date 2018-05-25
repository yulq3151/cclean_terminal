package com.cclean.terminal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.*;
import com.cclean.terminal.service.HotelService;
import com.cclean.terminal.service.OrderService;
import com.cclean.terminal.service.SkuService;
import com.cclean.terminal.service.UserService;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.util.InvokeUtil;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.util.Utils;
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
 * 供应商相关逻辑
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Value("${invoke.order.page}")
    private String orderPageUrl;

    @Value("${invoke.order.info}")
    private String orderInfoUrl;

    @Resource
    private HotelService hotelService;

    @Resource
    private SkuService skuService;

    @Resource
    private UserService userService;

    /**
     * 订单详情
     *
     * @param accessToken 授权码
     * @param idVO        参数（id）
     * @return
     * @throws BusinessException
     */
    @Override
    public Order info(String accessToken, IdVO idVO) throws BusinessException {
        Order order = new Order();
        String url = cloudUrl + orderInfoUrl;
        String js = JSONArray.toJSONString(idVO);
        JSONObject jsonParam = JSONArray.parseObject(js);
        String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
        logger.info("订单详情：respose:{}", httpEntitys);
        JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObj.getString("retCode");
        if (!"00000".equals(retCode)) {
            String retInfo = jsonObj.getString("retInfo");
            throw new BusinessException(retInfo, retCode);
        }
        JSONObject orderJson = jsonObj.getJSONObject("data");
        if (orderJson == null) {
            return null;
        }
        order.setId(orderJson.getString("id"));
        order.setType(orderJson.getInteger("orderType"));
        order.setState(orderJson.getInteger("orderState") == 50 ? 1 : orderJson.getInteger("orderState"));
        String uid = orderJson.getString("operator");
        order.setOperator(uid);
        order.setOrderDate(Utils.formatDate(orderJson.getString("expectDate")));
        order.setCreateTime(orderJson.getDate("createTime"));
        order.setModifyTime(orderJson.getDate("modifyTime"));
        Set<String> uids = new HashSet<>();
        uids.add(uid);
        Map<String, String> users = this.userService.findUsersByIds(uids, accessToken);
        order.setOperatorName(users.get(uid));
        Hotel hotel = this.hotelService.findHotelById(accessToken, new IdVO(orderJson.getString("hotelId")));
        order.setHotel(hotel);
        DeliveryPoint point = this.hotelService.findPointById(accessToken, new IdVO(orderJson.getString("pointId")));
        order.setDeliveryPoint(point);
        JSONArray skuListJson = orderJson.getJSONArray("skus");
        Map<String, Object> map = this.skuService.stringToBean(skuListJson, accessToken);
        List<SkuStatistics> list = (List<SkuStatistics>) map.get("skuStatisticsList");
        if (list!=null) {
            Collections.sort(list, Comparator.comparing(o -> o.getSku().getName()));
        }
        order.setSkuStatisticss(list);
        order.setSkuStatisTotal((Integer) map.get("total"));
        return order;

    }

    /**
     * 查询订单列表
     *
     * @param token
     * @param orderVO
     * @return
     */
    @Override
    public List<Order> list(String token, OrderVO orderVO) throws BusinessException {
        String url = cloudUrl + orderPageUrl;
        JSONObject param = InvokeUtil.jsonParam(orderVO, "");
        JSONObject data = InvokeUtil.invokeResult(url, token, param);
        logger.info("订单列表：respose:{}", data);
        List<Order> list = new ArrayList<>();
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
            Order order = new Order();
            order.setId(obj.getString("id"));
            order.setHotel(hotels.get(obj.getString("hotelId")));
            order.setDeliveryPoint(points.get(obj.getString("pointId")));
            order.setType(obj.getInteger("orderType"));
            order.setState(obj.getInteger("orderState") == 50 ? 1 : obj.getInteger("orderState"));
            order.setOperator(obj.getString("operator"));
            order.setOperatorName(users.get(obj.getString("operator")));
            order.setOrderDate(Utils.formatDate(obj.getString("expectDate")));
            order.setCreateTime(obj.getDate("createTime"));
            order.setModifyTime(obj.getDate("modifyTime"));
            int total = 0;
            JSONArray objskus = obj.getJSONArray("skus");
            List<SkuStatistics> skutics = new ArrayList<>();
            if (objskus != null && objskus.size() > 0) {
                for (int j = 0; j < objskus.size(); j++) {
                    JSONObject skujson = objskus.getJSONObject(j);
                    SkuStatistics skusta = new SkuStatistics();
                    String id = skujson.getString("skuId");
                    skusta.setSku(skus.get(id));
                    int count = StringUtils.toInt(skujson.getString("total"));
                    total += count;
                    skusta.setCount(count);
                    skusta.setActualCount(skujson.getInteger("normalCount"));
                    skusta.setDifferenceCount(skujson.getInteger("rewashCount"));
                    skutics.add(skusta);
                }
            }
            Collections.sort(skutics, Comparator.comparing(o -> o.getSku().getName()));
            order.setSkuStatisTotal(total);
            order.setSkuStatisticss(skutics);
            list.add(order);
        }
        return list;

    }

}
