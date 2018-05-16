package com.cclean.terminal.util;

import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.Result;
import com.cclean.terminal.vo.OrderVO;

public class InvokeUtil {

    //订单/配送单/任务单 默认分页显示10条
    private static final Integer PAGESIZE = 50;
    //第几页
    private static final Integer PAGENUM = 1;

    public static JSONObject jsonParam(OrderVO orderVO, String type) {
        JSONObject jsonParam = new JSONObject();
        String hotelId = orderVO.getHotelId();
        if (StringUtils.isNotBlank(hotelId)) {
            jsonParam.put("hotelId", hotelId);
        }

        String pointId = orderVO.getPointId();
        if (StringUtils.isNotBlank(pointId)) {
            jsonParam.put("pointId", pointId);
        }

        String factoryId = orderVO.getFactoryId();
        if (StringUtils.isNotBlank(factoryId)) {
            jsonParam.put("factoryId", factoryId);
        }

        Integer state = orderVO.getState();
        if (state != null) {
            jsonParam.put("state", state);
        }
        Integer orderState = orderVO.getOrderState();
        if (orderState != null) {
            jsonParam.put("orderState", orderState);
        }
        Integer orderType = orderVO.getOrderType();
        if (orderType != null) {
            jsonParam.put("orderType", orderType);
        }
        Integer checkState = orderVO.getCheckState();
        if (checkState != null) {
            jsonParam.put("checkState", checkState);
        }
        jsonParam.put("pageNum", orderVO.getPageNum() == null ? PAGENUM : orderVO.getPageNum());
        jsonParam.put("pageSize", orderVO.getPageSize() == null ? PAGESIZE : orderVO.getPageSize());
        if (type.equals("delivery")) {
            jsonParam.put("userId", orderVO.getUserId());
        }
        if (null != orderVO.getTime()) {
            jsonParam.put("startTime", orderVO.getTime() + " 00:00:00");
            jsonParam.put("endTime", orderVO.getTime() + " 23:59:59");

        } else {
            if (null != orderVO.getStartTime()) {
                jsonParam.put("startTime", orderVO.getStartTime() + " 00:00:00");
            }
            if (orderVO.getEndTime() != null) {
                jsonParam.put("endTime", orderVO.getEndTime() + " 23:59:59");
            }
        }
        return jsonParam;
    }

    public static JSONObject invokeResult(String url, String token, JSONObject jsonObject) throws BusinessException {
        String post = HttpUtil.doPost(url, token, jsonObject);
        JSONObject jsonObject1 = JSONObject.parseObject(post);
        if (!"00000".equals(jsonObject1.getString("retCode"))) {
            throw new BusinessException(jsonObject1.getString("retCode"), jsonObject1.getString("retInfo"));
        }
        JSONObject datajson = jsonObject1.getJSONObject("data");
        return datajson;
    }

    public static String invokeString(String url, String token, JSONObject jsonObject) throws BusinessException {
        String post = HttpUtil.doPost(url, token, jsonObject);
        JSONObject jsonObject1 = JSONObject.parseObject(post);
        if (!"00000".equals(jsonObject1.getString("retCode"))) {
            throw new BusinessException(jsonObject1.getString("retCode"), jsonObject1.getString("retInfo"));
        }
        return jsonObject1.getString("data");
    }

}
