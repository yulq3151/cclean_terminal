package com.cclean.terminal.vo;

import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 */
public class OrderVO extends PageVO {

    private String pointId; //配送点
    private String hotelId; //酒店
    private String factoryId; //工厂
    private Integer orderState; //0 未处理 50已处理 99终止
    private Integer state; //0 未处理 50已处理 99终止
    private Integer orderType; //1送洗收脏 2新酒店 3酒店下单4差异
    private Integer checkState; //0未复核1已复核
    private String time;  //当天时间
    private String startTime; //开始时间
    private String endTime; //结束时间
    private String userId; //
    private List<String> factoryIds;

    public List<String> getFactoryIds() {
        return factoryIds;
    }

    public void setFactoryIds(List<String> factoryIds) {
        this.factoryIds = factoryIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }


    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
