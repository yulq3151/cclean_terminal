package com.cclean.terminal.model;

import java.util.Date;
import java.util.List;

/**
 * 订单
 */
public class Order {

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
    // 类型
    private Integer type;
    // 状态
    private Integer state;
    // 操作人ID
    private String operator;
    // 操作人姓名
    private String operatorName;
    // 订单日期
    private Date orderDate;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
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
        return "Order{" +
                "id='" + id + '\'' +
                ", hotel=" + hotel +
                ", deliveryPoint=" + deliveryPoint +
                ", skuStatisticss=" + skuStatisticss +
                ", type=" + type +
                ", state=" + state +
                ", operator='" + operator + '\'' +
                ", orderDate=" + orderDate +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
