package com.cclean.terminal.model;

import java.util.Date;
import java.util.List;

/**
 * Sku扩展信息
 */
public class SkuExtend {

    // 未登记
    private List<String> unregisteredList;
    // 已登记
    private List<String> registeredList;
    // Sku统计信息
    private List<SkuStatistics> skuStatisticsList;
    // 配货状态
    private Integer deliveryState;
    // 收货状态
    private Integer receiveState;
    // 状态时间
    private Date stateTime;

    //已收货数量
    private Integer receivedCount;

    public Integer getReceivedCount() {
        return receivedCount;
    }

    public void setReceivedCount(Integer receivedCount) {
        this.receivedCount = receivedCount;
    }

    public List<String> getUnregisteredList() {
        return unregisteredList;
    }

    public void setUnregisteredList(List<String> unregisteredList) {
        this.unregisteredList = unregisteredList;
    }

    public List<String> getRegisteredList() {
        return registeredList;
    }

    public void setRegisteredList(List<String> registeredList) {
        this.registeredList = registeredList;
    }

    public List<SkuStatistics> getSkuStatisticsList() {
        return skuStatisticsList;
    }

    public void setSkuStatisticsList(List<SkuStatistics> skuStatisticsList) {
        this.skuStatisticsList = skuStatisticsList;
    }

    public Integer getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(Integer deliveryState) {
        this.deliveryState = deliveryState;
    }

    public Integer getReceiveState() {
        return receiveState;
    }

    public void setReceiveState(Integer receiveState) {
        this.receiveState = receiveState;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    @Override
    public String toString() {
        return "SkuExtend{" +
                "unregisteredList=" + unregisteredList +
                ", registeredList=" + registeredList +
                ", skuStatisticsList=" + skuStatisticsList +
                ", deliveryState=" + deliveryState +
                ", receiveState=" + receiveState +
                ", stateTime=" + stateTime +
                '}';
    }
}
