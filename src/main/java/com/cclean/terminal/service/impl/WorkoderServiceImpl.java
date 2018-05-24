package com.cclean.terminal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.*;
import com.cclean.terminal.service.HotelService;
import com.cclean.terminal.service.SkuService;
import com.cclean.terminal.service.UserService;
import com.cclean.terminal.service.WorkoderService;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.util.InvokeUtil;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.vo.IdVO;
import com.cclean.terminal.vo.OrderIdsVO;
import com.cclean.terminal.vo.OrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 任务单操作相关逻辑
 */
@Service
public class WorkoderServiceImpl implements WorkoderService {
    private static Logger logger = LoggerFactory.getLogger(WorkoderServiceImpl.class);

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Value("${invoke.pickorder.page}")
    private String workorderPageUrl;

    @Value("${invoke.pickorder.info}")
    private String workorderInfoUrl;

    @Value("${invoke.orderToWork}")
    private String orderTOWork;

    @Resource
    private HotelService hotelService;

    @Resource
    private SkuService skuService;

    @Resource
    private UserService userService;

    /**
     * 生成任务单
     *
     * @param accessToken
     * @param orderIdsVO
     * @return
     * @throws BusinessException
     */
    @Override
    public WorkOrder generate(String accessToken, OrderIdsVO orderIdsVO) throws BusinessException {
        String url = linenUrl + orderTOWork;
        String js = JSONArray.toJSONString(orderIdsVO);
        String httpEntitys = HttpUtil.doPost(url, accessToken, JSONArray.parseObject(js));
        logger.info("生成任务单 Responses: {}", httpEntitys);
        JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObj.getString("retCode");
        if (!"00000".equals(retCode)) {
            String retInfo = jsonObj.getString("retInfo");
            throw new BusinessException(retCode, retInfo);
        }
        JSONObject data = jsonObj.getJSONObject("data");
        if (data == null || data.getString("id") == null) {
            return null;
        }
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(data.getString("id"));
        workOrder.setState(data.getInteger("state") == 50 ? 1 : data.getInteger("state"));
        String uid = data.getString("operator");
        workOrder.setOperator(uid);
        workOrder.setOrderDate(new Date());
        workOrder.setCreateTime(new Date());
        workOrder.setModifyTime(new Date());
        Set<String> uids = new HashSet<>();
        uids.add(uid);
        Map<String, String> users = this.userService.findUsersByIds(uids, accessToken);
        workOrder.setOperatorName(users.get(uid));
        Hotel hotel = this.hotelService.findHotelById(accessToken, new IdVO(data.getString("hotelId")));
        workOrder.setHotel(hotel);
        DeliveryPoint point = this.hotelService.findPointById(accessToken, new IdVO(data.getString("pointId")));
        workOrder.setDeliveryPoint(point);
        JSONArray skuListJson = data.getJSONArray("skus");
        Map<String, Object> map = this.skuService.stringToBean(skuListJson, accessToken);
        List<SkuStatistics> list = (List<SkuStatistics>) map.get("skuStatisticsList");
        Collections.sort(list, Comparator.comparing(o -> o.getSku().getName()));
        workOrder.setSkuStatisticss(list);
        workOrder.setSkuStatisTotal((Integer) map.get("total"));
        return workOrder;
    }


    /**
     * 查询任务单详情
     *
     * @param accessToken
     * @param idVO
     * @return
     * @throws BusinessException
     */
    @Override
    public WorkOrder info(String accessToken, IdVO idVO) throws BusinessException {
        WorkOrder workOrder = new WorkOrder();
        String url = cloudUrl + workorderInfoUrl;
        String js = JSONArray.toJSONString(idVO);
        JSONObject jsonParam = JSONArray.parseObject(js);
        String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
        logger.info("任务单详情 Responses: {}", httpEntitys);
        JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObj.getString("retCode");
        if (!"00000".equals(retCode)) {
            String retInfo = jsonObj.getString("retInfo");
            throw new BusinessException(retCode, retInfo);
        }
        JSONObject dataJson = jsonObj.getJSONObject("data");
        if (dataJson == null) {
            return null;
        }
        workOrder.setId(dataJson.getString("id"));
        workOrder.setState(dataJson.getInteger("state") == 50 ? 1 : dataJson.getInteger("state"));
        String uid = dataJson.getString("operator");
        workOrder.setOperator(uid);
        workOrder.setOrderDate(dataJson.getDate("createTime"));
        workOrder.setCreateTime(dataJson.getDate("createTime"));
        workOrder.setModifyTime(dataJson.getDate("modifyTime"));
        Set<String> uids = new HashSet<>();
        uids.add(uid);
        Map<String, String> users = this.userService.findUsersByIds(uids, accessToken);
        workOrder.setOperatorName(users.get(uid));
        Hotel hotel = this.hotelService.findHotelById(accessToken, new IdVO(dataJson.getString("hotelId")));
        workOrder.setHotel(hotel);
        DeliveryPoint point = this.hotelService.findPointById(accessToken, new IdVO(dataJson.getString("pointId")));
        workOrder.setDeliveryPoint(point);

        JSONArray skuListJson = dataJson.getJSONArray("skus");
        Map<String, Object> map = this.skuService.stringToBean(skuListJson, accessToken);
        List<SkuStatistics> list = (List<SkuStatistics>) map.get("skuStatisticsList");
        Collections.sort(list, Comparator.comparing(o -> o.getSku().getName()));
        workOrder.setSkuStatisticss(list);
        workOrder.setSkuStatisTotal((Integer) map.get("total"));
        return workOrder;
    }

    /**
     * 查询任务单列表
     *
     * @param token
     * @param orderVO
     * @return
     * @throws BusinessException
     */
    @Override
    public List<WorkOrder> list(String token, OrderVO orderVO) throws BusinessException {
        List<WorkOrder> list = new ArrayList<>();
        String url = cloudUrl + workorderPageUrl;
        JSONObject param = InvokeUtil.jsonParam(orderVO, "");
        JSONObject data = InvokeUtil.invokeResult(url, token, param);
        logger.info("任务单列表 Responses: {}", data);
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
            WorkOrder workOrder = new WorkOrder();
            workOrder.setId(obj.getString("id"));
            workOrder.setHotel(hotels.get(obj.getString("hotelId")));
            workOrder.setDeliveryPoint(points.get(obj.getString("pointId")));
            workOrder.setState(obj.getInteger("state") == 50 ? 1 : obj.getInteger("state"));
            workOrder.setOperator(obj.getString("operator"));
            workOrder.setOperatorName(users.get(obj.getString("operator")));
            workOrder.setOrderDate(obj.getDate("createTime"));
            workOrder.setCreateTime(obj.getDate("createTime"));
            workOrder.setModifyTime(obj.getDate("modifyTime"));
            int total = 0;
            JSONArray objskus = obj.getJSONArray("skus");
            List<SkuStatistics> skutics = new ArrayList<>();
            if (objskus != null && objskus.size() > 0) {
                for (int j = 0; j < objskus.size(); j++) {
                    JSONObject skujson = objskus.getJSONObject(j);
                    SkuStatistics skusta = new SkuStatistics();
                    String id = skujson.getString("skuId");
                    skusta.setSku(skus.get(id));
                    int count = StringUtils.toInt(skujson.getString("expectCount"));
                    skusta.setCount(count);
                    total += count;
                    skusta.setActualCount(skujson.getInteger("realCount"));
                    skusta.setDifferenceCount(skujson.getInteger("diffCount"));
                    skutics.add(skusta);
                }
            }
            Collections.sort(skutics, Comparator.comparing(o -> o.getSku().getName()));
            workOrder.setSkuStatisTotal(total);
            workOrder.setSkuStatisticss(skutics);
            list.add(workOrder);
        }
        return list;
    }

}
