package com.cclean.terminal.model;

import java.util.Date;
import java.util.List;

/**
 * 配货单
 */
public class DeliveryReceipt {

    // id
    private String id;
    // 酒店
    private Hotel hotel;
    // 配送点
    private DeliveryPoint deliveryPoint;
    // Sku列表
    private List<SkuStatistics> skuStatisticss;
    //sku总数
    private Integer skuStatisTotal;
    // 状态
    private Integer state;
    // 操作人ID
    private String operator;
    // 操作人姓名
    private String operatorName;
    // 配送时间
    private Date deliveryTime;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date modifyTime;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public DeliveryPoint getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

    public List<SkuStatistics> getSkuStatisticss() {
        return skuStatisticss;
    }

    public void setSkuStatisticss(List<SkuStatistics> skuStatisticss) {
        this.skuStatisticss = skuStatisticss;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getSkuStatisTotal() {
        return skuStatisTotal;
    }

    public void setSkuStatisTotal(Integer skuStatisTotal) {
        this.skuStatisTotal = skuStatisTotal;
    }

    @Override
    public String toString() {
        return "DeliveryReceipt{" +
                "id='" + id + '\'' +
                ", hotel=" + hotel +
                ", deliveryPoint=" + deliveryPoint +
                ", skuStatistics=" + skuStatisticss +
                ", state=" + state +
                ", operator='" + operator + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
