package com.cclean.terminal.mobileService.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.mobileService.ConService;
import com.cclean.terminal.mobileService.HotelMService;
import com.cclean.terminal.mobileService.OrderMService;
import com.cclean.terminal.model.Sku;
import com.cclean.terminal.model2.*;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.util.InvokeUtil;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.vo.OrderIdsVO;
import com.cclean.terminal.vo.OrderVO;
import com.cclean.terminal.vo.SkuSVo;
import com.cclean.terminal.vo.ZPickVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author yulq
 * @create 2018-04-12 21:36
 * @desc
 **/
@Service
public class OrderMServiceImpl implements OrderMService {
    private final static Logger logger = LoggerFactory.getLogger(OrderMServiceImpl.class);

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Value("${msg.url}")
    private String msgUrl;

    @Value("${invoke.order.page}")
    private String orderPageUrl;

    @Value("${invoke.order.info}")
    private String orderInfoUrl;

    @Value("${invoke.pickorder.page}")
    private String workorderPageUrl;

    @Value("${invoke.pickorder.info}")
    private String workorderInfoUrl;

    @Value("${invoke.orderToWork}")
    private String orderTOWork;

    @Value("${invoke.deliveryorder.page}")
    private String deliveryorderPageUrl;

    @Value("${invoke.deliveryorder.info}")
    private String deliveryorderInfoUrl;

    @Resource
    private HotelMService hotelMService;

    @Resource
    private ConService conService;

    /**
     * 订单列表-显示配送点信息，日期，sku总数
     *
     * @param token
     * @param
     * @return
     * @throws BusinessException
     */
    @Override
    public PageMo orderlist(String token, OrderVO orderVO) throws BusinessException {
        String url = cloudUrl + orderPageUrl;
        JSONObject param = InvokeUtil.jsonParam(orderVO, "order");
        JSONObject data = InvokeUtil.invokeResult(url, token, param);
        if (data == null) {
            return new PageMo();
        }
        Integer total = data.getInteger("total");
        List<Order> list = JSONArray.parseArray(data.getString("list"), Order.class);
        if (list == null || list.size() == 0) {
            return new PageMo();
        }
        Set<String> hotelSet = new HashSet<>();
        Set<String> pointSet = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            Order order = list.get(i);
            hotelSet.add(order.getHotelId());
            pointSet.add(order.getPointId());
        }

        Map<String, HotelBo> hotels = this.hotelMService.findHotelsByIds(hotelSet);
        Map<String, DeliveryPointM> points = this.conService.findPointsByIds(pointSet);

        for (int i = 0; i < list.size(); i++) {
            Order order = list.get(i);
            order.setHotel(hotels.get(order.getHotelId()));
            order.setDrypoint(points.get(order.getPointId()));
        }
        return new PageMo(list, orderVO.getPageNum(), orderVO.getPageSize(), total);

    }

    /**
     * 订单详情
     *
     * @param token
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public Order orderinfo(String token, String id) throws BusinessException {
        String url = cloudUrl + orderInfoUrl;
        JSONObject param = new JSONObject();
        param.put("id", id);
        JSONObject data = InvokeUtil.invokeResult(url, token, param);
        if (data == null) {
            return null;
        }
        Order order = JSONObject.parseObject(data.toJSONString(), Order.class);
        if (order == null) {
            return null;
        }
        Set<String> hotelSet = new HashSet<>();
        Set<String> pointSet = new HashSet<>();
        Set<String> skuSet = new HashSet<>();
        Set<String> userSet = new HashSet<>();
        hotelSet.add(order.getHotelId());
        pointSet.add(order.getPointId());
        userSet.add(order.getOperator());
        OrderSku[] skusta = order.getSkus();
        for (int i = 0; i < skusta.length; i++) {
            skuSet.add(skusta[i].getSkuId());
        }
        Map<String, HotelBo> hotels = this.hotelMService.findHotelsByIds(hotelSet);
        Map<String, DeliveryPointM> points = this.conService.findPointsByIds(pointSet);
        Map<String, String> users = this.conService.findUsersByIds(userSet, token);
        Map<String, Sku> skus = this.conService.findSkusByIds(skuSet);

        order.setHotel(hotels.get(order.getHotelId()));
        order.setDrypoint(points.get(order.getPointId()));
        order.setOperatorName(users.get(order.getOperator()));
        for (int i = 0; i < skusta.length; i++) {
            Sku sku = skus.get(skusta[i].getSkuId());
            if (sku != null) {
                skusta[i].setSkuName(sku.getName());
            }
        }
        Collections.sort(Arrays.asList(skusta),Comparator.comparing(OrderSku::getSkuName));
        return order;
    }


    /**
     * 订单生成任务单
     *
     * @param token
     * @param orderIdsVO
     * @return
     * @throws BusinessException
     */
    @Override
    public PickorderOrder createPickOrder(String token, OrderIdsVO orderIdsVO) throws BusinessException {
        String url = linenUrl + orderTOWork;
        JSONObject parpam = new JSONObject();
        parpam.put("orderIds", orderIdsVO.getOrderIds());
        JSONObject data = InvokeUtil.invokeResult(url, token, parpam);
        PickorderOrder pickorderOrder = JSONObject.parseObject(data.toJSONString(), PickorderOrder.class);
        if (pickorderOrder == null) {
            throw new BusinessException("00001", "生成任务单失败");
        }
        return pickorderOrder;
    }


    /**
     * 任务单列表
     *
     * @param token
     * @param orderVO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageMo pickorderlist(String token, OrderVO orderVO) throws BusinessException {
        String url = cloudUrl + workorderPageUrl;
        JSONObject param = InvokeUtil.jsonParam(orderVO, "");
        JSONObject data = InvokeUtil.invokeResult(url, token, param);
        Integer total = data.getInteger("total");
        List<PickorderOrder> list = JSONArray.parseArray(data.getString("list"), PickorderOrder.class);
        if (list == null || list.size() == 0) {
            return new PageMo();
        }
        Set<String> hotelSet = new HashSet<>();
        Set<String> pointSet = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            PickorderOrder pickorder = list.get(i);
            hotelSet.add(pickorder.getHotelId());
            pointSet.add(pickorder.getPointId());
        }

        Map<String, HotelBo> hotels = this.hotelMService.findHotelsByIds(hotelSet);
        Map<String, DeliveryPointM> points = this.conService.findPointsByIds(pointSet);
        for (int i = 0; i < list.size(); i++) {
            PickorderOrder pickorder = list.get(i);
            pickorder.setHotel(hotels.get(pickorder.getHotelId()));
            pickorder.setDrypoint(points.get(pickorder.getPointId()));
            pickorder.setTotalCnt(pickorder.getExpectCount());
        }
        return new PageMo(list, orderVO.getPageNum(), orderVO.getPageSize(), total);
    }

    /**
     * 任务单详情
     *
     * @param token
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public PickorderOrder pickorderinfo(String token, String id) throws BusinessException {
        String url = cloudUrl + workorderInfoUrl;
        JSONObject param = new JSONObject();
        param.put("id", id);
        JSONObject data = InvokeUtil.invokeResult(url, token, param);
        if (data == null) {
            return null;
        }
        PickorderOrder pickorderOrder = JSONObject.parseObject(data.toJSONString(), PickorderOrder.class);
        if (pickorderOrder == null) {
            throw null;
        }
        Set<String> hotelSet = new HashSet<>();
        Set<String> pointSet = new HashSet<>();
        Set<String> skuSet = new HashSet<>();
        Set<String> userSet = new HashSet<>();
        hotelSet.add(pickorderOrder.getHotelId());
        pointSet.add(pickorderOrder.getPointId());
        userSet.add(pickorderOrder.getOperator());
        List<PickOrderSku> skusta = pickorderOrder.getSkus();
        if (skusta != null && skusta.size() > 0) {
            for (int i = 0; i < skusta.size(); i++) {
                skuSet.add(skusta.get(i).getSkuId());
            }
        }
        Map<String, HotelBo> hotels = this.hotelMService.findHotelsByIds(hotelSet);
        Map<String, DeliveryPointM> points = this.conService.findPointsByIds(pointSet);
        Map<String, String> users = this.conService.findUsersByIds(userSet, token);


        pickorderOrder.setHotel(hotels.get(pickorderOrder.getHotelId()));
        pickorderOrder.setDrypoint(points.get(pickorderOrder.getPointId()));
        pickorderOrder.setOperatorName(users.get(pickorderOrder.getOperator()));
        pickorderOrder.setTotalCnt(pickorderOrder.getExpectCount());
        if (!skuSet.isEmpty()) {
            Map<String, Sku> skus = this.conService.findSkusByIds(skuSet);
            List<PickOrderSku> skulist = new ArrayList<>();
            for (int i = 0; i < skusta.size(); i++) {
                PickOrderSku orderSku = skusta.get(i);
                Sku sku = skus.get(orderSku.getSkuId());
                if (sku != null) {
                    orderSku.setSkuName(sku.getName());
                    //计算扎数
                    int count = sku.getPackCnt() == 0 ? 1 : sku.getPackCnt(); //sku的标准捆扎数，最低单位为1
                    int exount = orderSku.getExpectCount() == null ? 0 : orderSku.getExpectCount(); //应配数量
                    if (exount == 0) {
                        orderSku.setZpick(0);
                        skulist.add(orderSku);
                    } else if (exount <= count) {
                        orderSku.setZpick(1);
                    } else {
                        int picount = exount % count == 0 ? exount / count : (exount / count + 1);
                        orderSku.setZpick(picount);
                    }
                }
            }
            skusta.removeAll(skulist);
            Collections.sort(skusta,Comparator.comparing(PickOrderSku::getSkuName));
        }
        return pickorderOrder;
    }


    /**
     * 任务单生成配送单
     *
     * @param token
     * @param
     * @return
     */
    @Override
    public DeliveryOrder createDeliveryOrder(String token, ZPickVo zPickVo) throws BusinessException {
        String url = linenUrl + "/server/order/deliveryorder/generate";
        List<Map<String, Object>> list = new ArrayList<>();
        SkuSVo[] skuSVos = zPickVo.getSkuSVos();
        Set<String> skuSet = new HashSet<>();
        for (int i = 0; i < skuSVos.length; i++) {
            skuSet.add(skuSVos[i].getSkuId());
        }
        if (!skuSet.isEmpty()) {
            Map<String, Sku> skus = this.conService.findSkusByIds(skuSet);
            //任务单里入参是扎数，这里转换为数量
            for (int i = 0; i < skuSVos.length; i++) {
                SkuSVo skuSVo = skuSVos[i];
                Sku sku = skus.get(skuSVo.getSkuId());
                //每扎的标准数 最低单位是1
                Integer packCnt = (sku.getPackCnt() == null ? 1 : sku.getPackCnt());
                //实际数量
                int szpi = packCnt * skuSVo.getScanZpick();
                Map<String, Object> map = new HashMap<>();
                map.put("skuId", skuSVo.getSkuId());
                map.put("count", 0); //应配数量
                map.put("actualCount", szpi); //实配数量
                map.put("differenceCount", 0); //差异数量
                list.add(map);
            }
        }
        String[] rfids = zPickVo.getRfids();
        JSONObject param = new JSONObject();
        param.put("pickOrderId", zPickVo.getWorkOrderId());
        param.put("skus", list);
        param.put("rfids", rfids);
        param.put("packages",zPickVo.getPackages());

        //请求生成配送单接口
        String post = HttpUtil.doPost(url, token, param);
        JSONObject obj = JSONObject.parseObject(post);
        String retCode = obj.getString("retCode");
        if (!"00000".equals(retCode)) {
            if ("00203".equals(retCode)) {
                throw new BusinessException("00001","有布草已配送");
            }
            throw new BusinessException(retCode,obj.getString("retInfo"));
        }
        String datajson = obj.getString("data");
        //生成配送单成功后，改变打扎状态
        this.conService.updatepack(token,Arrays.asList(zPickVo.getPackids()),"1");
        DeliveryOrder deliveryOrder = JSONObject.parseObject(datajson, DeliveryOrder.class);
        //调用接口推送消息
        logger.info("配送单消息推送开始：{}",System.currentTimeMillis());
        JSONObject mparam = new JSONObject();
        mparam.put("type",1);
        mparam.put("orderId",deliveryOrder.getId());
        mparam.put("hotelId",deliveryOrder.getHotelId());
        mparam.put("pointId",deliveryOrder.getPointId());
        HttpUtil.doPost(msgUrl, token,mparam);
        logger.info("配送单消息推送结束：{}",System.currentTimeMillis());
        return deliveryOrder;

    }

    /**
     * 我的配送单
     *
     * @param token
     * @param orderVO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageMo myDeliveryorderlist(String token, OrderVO orderVO) throws BusinessException {
        String url = cloudUrl + deliveryorderPageUrl;
        orderVO.setUserId(token); //app使用者
        JSONObject param = InvokeUtil.jsonParam(orderVO, "delivery");
        JSONObject data = InvokeUtil.invokeResult(url, token, param);
        if (data == null) {
            return new PageMo();
        }
        Integer total = data.getInteger("total");
        List<DeliveryOrder> list = JSONArray.parseArray(data.getString("list"), DeliveryOrder.class);
        if (list == null || list.size() == 0) {
            return new PageMo();
        }
        Set<String> hotelSet = new HashSet<>();
        Set<String> pointSet = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            DeliveryOrder deOrder = list.get(i);
            hotelSet.add(deOrder.getHotelId());
            pointSet.add(deOrder.getPointId());
        }
        Map<String, HotelBo> hotels = this.hotelMService.findHotelsByIds(hotelSet);
        Map<String, DeliveryPointM> points = this.conService.findPointsByIds(pointSet);
        for (int i = 0; i < list.size(); i++) {
            DeliveryOrder deOrder = list.get(i);
            deOrder.setHotel(hotels.get(deOrder.getHotelId()));
            deOrder.setDrypoint(points.get(deOrder.getPointId()));
            DeliveryOrderSku[] deskus = deOrder.getSkus();
            Integer totalcnt = 0;
            if (deskus != null && deskus.length > 0) {
                for (int j = 0; j < deskus.length; j++) {
                    int count = deskus[j].getDeliveryCount() == null ? 0 : deskus[j].getDeliveryCount();
                    totalcnt += count;
                }
            }
            deOrder.setTotalCnt(totalcnt);
        }
        return new PageMo(list, orderVO.getPageNum(), orderVO.getPageSize(), total);
    }

    /**
     * 配送单列表
     *
     * @param token
     * @param orderVO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageMo deliveryorderlist(String token, OrderVO orderVO) throws BusinessException {
        String url = cloudUrl + deliveryorderPageUrl;
        JSONObject param = InvokeUtil.jsonParam(orderVO, "");
        JSONObject data = InvokeUtil.invokeResult(url, token, param);
        if (data == null) {
            return new PageMo();
        }
        Integer total = data.getInteger("total");
        List<DeliveryOrder> list = JSONArray.parseArray(data.getString("list"), DeliveryOrder.class);
        if (list == null || list.size() == 0) {
            return new PageMo();
        }
        Set<String> hotelSet = new HashSet<>();
        Set<String> pointSet = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            DeliveryOrder deOrder = list.get(i);
            hotelSet.add(deOrder.getHotelId());
            pointSet.add(deOrder.getPointId());
        }
        Map<String, HotelBo> hotels = this.hotelMService.findHotelsByIds(hotelSet);
        Map<String, DeliveryPointM> points = this.conService.findPointsByIds(pointSet);
        for (int i = 0; i < list.size(); i++) {
            DeliveryOrder deOrder = list.get(i);
            deOrder.setHotel(hotels.get(deOrder.getHotelId()));
            deOrder.setDrypoint(points.get(deOrder.getPointId()));
            DeliveryOrderSku[] deskus = deOrder.getSkus();
            Integer totalcnt = 0;
            if (deskus != null && deskus.length > 0) {
                for (int j = 0; j < deskus.length; j++) {
                    int count = deskus[j].getDeliveryCount() == null ? 0 : deskus[j].getDeliveryCount();
                    totalcnt += count;
                }
            }
            deOrder.setTotalCnt(totalcnt);
        }
        return new PageMo(list, orderVO.getPageNum(), orderVO.getPageSize(), total);
    }

    /**
     * 配送单详情
     *
     * @param token
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public DeliveryOrder deliveryOrderinfo(String token, String id) throws BusinessException {
        String url = cloudUrl + deliveryorderInfoUrl;
        JSONObject param = new JSONObject();
        param.put("id", id);
        JSONObject data = InvokeUtil.invokeResult(url, token, param);
        if (data == null) {
            return null;
        }
        DeliveryOrder dryOrder = JSONObject.parseObject(data.toJSONString(), DeliveryOrder.class);
        if (dryOrder == null) {
            return null;
        }
        Set<String> hotelSet = new HashSet<>();
        Set<String> pointSet = new HashSet<>();
        Set<String> skuSet = new HashSet<>();
        Set<String> userSet = new HashSet<>();
        hotelSet.add(dryOrder.getHotelId());
        pointSet.add(dryOrder.getPointId());
        userSet.add(dryOrder.getOperator());
        DeliveryOrderSku[] dryskus = dryOrder.getSkus();
        for (int i = 0; i < dryskus.length; i++) {
            skuSet.add(dryskus[i].getSkuId());
        }
        Map<String, HotelBo> hotels = this.hotelMService.findHotelsByIds(hotelSet);
        Map<String, DeliveryPointM> points = this.conService.findPointsByIds(pointSet);
        Map<String, String> users = this.conService.findUsersByIds(userSet, token);


        dryOrder.setHotel(hotels.get(dryOrder.getHotelId()));
        dryOrder.setDrypoint(points.get(dryOrder.getPointId()));
        dryOrder.setOperatorName(users.get(dryOrder.getOperator()));
        if (!skuSet.isEmpty()) {
            int total = 0;
            Map<String, Sku> skus = this.conService.findSkusByIds(skuSet);
            for (int i = 0; i < dryskus.length; i++) {
                Sku sku = skus.get(dryskus[i].getSkuId());
                Integer count = dryskus[i].getDeliveryCount();
                total += (count == null ? 0 : count);
                if (sku != null) {
                    dryskus[i].setSkuName(sku.getName());
                }
            }
            dryOrder.setTotalCnt(total);
            Collections.sort(Arrays.asList(dryskus),Comparator.comparing(DeliveryOrderSku::getSkuName));
        }

        return dryOrder;
    }

    /**
     * 修改配送单的袋子
     * @param token
     * @param deliveryId
     * @param packageCodes
     * @return
     */
    @Override
    public boolean updateDeliveryOrderBag(String token, String deliveryId, List<String> packageCodes) throws BusinessException {
        if (StringUtils.isBlank(deliveryId)) {
            throw new BusinessException("00001","缺少参数：配送单ID");
        }
        List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < packageCodes.size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("packageCode",packageCodes.get(i));
            list.add(obj);
        }
        String url = cloudUrl+"/cloud/order/deliveryorder/update";
        JSONObject param = new JSONObject();
        param.put("id",deliveryId);
        param.put("packages",list);
        InvokeUtil.invokeString(url, token, param);
        return true;
    }
}
