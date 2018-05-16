package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 * @author yulq
 * @create 2018-04-12 13:22
 * @desc 订单的sku信息
 **/
public class OrderSku implements Serializable{

    private static final long serialVersionUID = 8953464523969547361L;
    //
    private String id;
    //订单ID
    private String orderId;
    //SkuId
    private String skuId;
    //Sku名称
    private String skuName;
    //Sku总数
    private Integer total;
    //正常数量
    private Integer normalCount;
    //返洗数量
    private Integer rewashCount;
    //操作人ID
    private String operator;
//    //创建时间
//    private String createTime;
//    //更新时间
//    private String modifyTime;
    //尺寸
//    private String sizeValue;

//    public String getSizeValue() {
//        return sizeValue;
//    }
//
//    public void setSizeValue(String sizeValue) {
//        this.sizeValue = sizeValue;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getNormalCount() {
        return normalCount;
    }

    public void setNormalCount(Integer normalCount) {
        this.normalCount = normalCount;
    }

    public Integer getRewashCount() {
        return rewashCount;
    }

    public void setRewashCount(Integer rewashCount) {
        this.rewashCount = rewashCount;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
//
//    public String getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(String createTime) {
//        this.createTime = createTime;
//    }
//
//    public String getModifyTime() {
//        return modifyTime;
//    }
//
//    public void setModifyTime(String modifyTime) {
//        this.modifyTime = modifyTime;
//    }

    @Override
    public String toString() {
        return "OrderSku{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", skuName='" + skuName + '\'' +
                ", total=" + total +
                ", normalCount=" + normalCount +
                ", rewashCount=" + rewashCount +
                ", operator='" + operator + '\'' +
//                ", createTime='" + createTime + '\'' +
//                ", modifyTime='" + modifyTime + '\'' +
                '}';
    }
}
