package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 *  配送单sku信息
 * @author yulq
 * @create 2018-04-12 13:34
 * @desc
 **/
public class DeliveryOrderSku implements Serializable{

    private static final long serialVersionUID = -5636796602078839876L;

    //
    private String id;
    //配送单id
    private  String deliveryOrderId;
    //SkuId
    private  String skuId;
    //Sku名称
    private  String skuName;
    //配送数量
    private  Integer deliveryCount;
    //实配数量
    private  Integer sacnCount;
    //操作人
    private  String operator;
//    //创建时间
//    private  String createTime;
//    //更新时间
//    private  String modifyTime;

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

    public String getDeliveryOrderId() {
        return deliveryOrderId;
    }

    public void setDeliveryOrderId(String deliveryOrderId) {
        this.deliveryOrderId = deliveryOrderId;
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

    public Integer getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(Integer deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    public Integer getSacnCount() {
        return sacnCount;
    }

    public void setSacnCount(Integer sacnCount) {
        this.sacnCount = sacnCount;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

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
        return "DeliveryOrderSku{" +
                "id='" + id + '\'' +
                ", deliveryOrderId='" + deliveryOrderId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", skuName='" + skuName + '\'' +
                ", deliveryCount=" + deliveryCount +
                ", sacnCount=" + sacnCount +
                ", operator='" + operator + '\'' +
//                ", createTime='" + createTime + '\'' +
//                ", modifyTime='" + modifyTime + '\'' +
                '}';
    }
}
