package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 * @author yulq
 * @create 2018-04-12 13:55
 * @desc 任务单sku信息
 **/
public class PickOrderSku implements Serializable{


    private static final long serialVersionUID = -7555700364570479376L;

    private String id;
    //任务单id
    private String pickOrderId;
    //SkuId
    private String skuId;
    //Sku名称
    private String skuName;
    //应配数量
    private Integer expectCount;
    //实配数量
    private Integer realCount;
    //差异数量
    private Integer diffCount;
    //操作人
    private String operator;
//    //创建时间
//    private String createTime;
//    //更新时间
//    private String modifyTime;
    //扎数 应配扎数
    private Integer zpick;
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

    public String getPickOrderId() {
        return pickOrderId;
    }

    public void setPickOrderId(String pickOrderId) {
        this.pickOrderId = pickOrderId;
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

    public Integer getExpectCount() {
        return expectCount;
    }

    public void setExpectCount(Integer expectCount) {
        this.expectCount = expectCount;
    }

    public Integer getRealCount() {
        return realCount;
    }

    public void setRealCount(Integer realCount) {
        this.realCount = realCount;
    }

    public Integer getDiffCount() {
        return diffCount;
    }

    public void setDiffCount(Integer diffCount) {
        this.diffCount = diffCount;
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

    public Integer getZpick() {
        return zpick;
    }

    public void setZpick(Integer zpick) {
        this.zpick = zpick;
    }

    @Override
    public String toString() {
        return "PickOrderSku{" +
                "id='" + id + '\'' +
                ", pickOrderId='" + pickOrderId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", skuName='" + skuName + '\'' +
                ", expectCount=" + expectCount +
                ", realCount=" + realCount +
                ", diffCount=" + diffCount +
                ", operator='" + operator + '\'' +
//                ", createTime='" + createTime + '\'' +
//                ", modifyTime='" + modifyTime + '\'' +
                '}';
    }
}
