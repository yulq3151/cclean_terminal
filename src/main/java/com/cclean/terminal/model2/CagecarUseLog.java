package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 * @author yulq
 * @create 2018-05-23 13:22
 * @desc    笼车使用记录
 **/
public class CagecarUseLog implements Serializable {

    //记录ID
    private String id;
    //记录的当前状态
    private Integer status;
    //笼车code
    private String cagecarCode;
    //工厂ID
    private String factoryId;
    //借用人
    private String borrowDriverId;
    //借用时间
    private String borrowTime;
    //借出人
    private String borrowCreator;
    //工厂签收时间
    private String borrowSignTime;
    //工厂签收人
    private String borrowReceiver;
    //工厂签收备注
    private String borrowSignRemark;
    //装货时间
    private String loadingTime;
    //装货操作人
    private String loadingCreator;
    //货物领取人
    private String deliveryDriverId;
    //货物领取时间
    private String deliveryTime;
    //货物发放人
    private String deliveryCreator;
    //中心签收时间
    private String deliverySignTime;
    //中心签收人
    private String deliveryReceiver;
    //中心签收备注
    private String deliverySignRemark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCagecarCode() {
        return cagecarCode;
    }

    public void setCagecarCode(String cagecarCode) {
        this.cagecarCode = cagecarCode;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getBorrowDriverId() {
        return borrowDriverId;
    }

    public void setBorrowDriverId(String borrowDriverId) {
        this.borrowDriverId = borrowDriverId;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getBorrowCreator() {
        return borrowCreator;
    }

    public void setBorrowCreator(String borrowCreator) {
        this.borrowCreator = borrowCreator;
    }

    public String getBorrowSignTime() {
        return borrowSignTime;
    }

    public void setBorrowSignTime(String borrowSignTime) {
        this.borrowSignTime = borrowSignTime;
    }

    public String getBorrowReceiver() {
        return borrowReceiver;
    }

    public void setBorrowReceiver(String borrowReceiver) {
        this.borrowReceiver = borrowReceiver;
    }

    public String getBorrowSignRemark() {
        return borrowSignRemark;
    }

    public void setBorrowSignRemark(String borrowSignRemark) {
        this.borrowSignRemark = borrowSignRemark;
    }

    public String getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(String loadingTime) {
        this.loadingTime = loadingTime;
    }

    public String getLoadingCreator() {
        return loadingCreator;
    }

    public void setLoadingCreator(String loadingCreator) {
        this.loadingCreator = loadingCreator;
    }

    public String getDeliveryDriverId() {
        return deliveryDriverId;
    }

    public void setDeliveryDriverId(String deliveryDriverId) {
        this.deliveryDriverId = deliveryDriverId;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryCreator() {
        return deliveryCreator;
    }

    public void setDeliveryCreator(String deliveryCreator) {
        this.deliveryCreator = deliveryCreator;
    }

    public String getDeliverySignTime() {
        return deliverySignTime;
    }

    public void setDeliverySignTime(String deliverySignTime) {
        this.deliverySignTime = deliverySignTime;
    }

    public String getDeliveryReceiver() {
        return deliveryReceiver;
    }

    public void setDeliveryReceiver(String deliveryReceiver) {
        this.deliveryReceiver = deliveryReceiver;
    }

    public String getDeliverySignRemark() {
        return deliverySignRemark;
    }

    public void setDeliverySignRemark(String deliverySignRemark) {
        this.deliverySignRemark = deliverySignRemark;
    }

    @Override
    public String toString() {
        return "CagecarUseLog{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", cagecarCode='" + cagecarCode + '\'' +
                ", factoryId='" + factoryId + '\'' +
                ", borrowDriverId='" + borrowDriverId + '\'' +
                ", borrowTime='" + borrowTime + '\'' +
                ", borrowCreator='" + borrowCreator + '\'' +
                ", borrowSignTime='" + borrowSignTime + '\'' +
                ", borrowReceiver='" + borrowReceiver + '\'' +
                ", borrowSignRemark='" + borrowSignRemark + '\'' +
                ", loadingTime='" + loadingTime + '\'' +
                ", loadingCreator='" + loadingCreator + '\'' +
                ", deliveryDriverId='" + deliveryDriverId + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", deliveryCreator='" + deliveryCreator + '\'' +
                ", deliverySignTime='" + deliverySignTime + '\'' +
                ", deliveryReceiver='" + deliveryReceiver + '\'' +
                ", deliverySignRemark='" + deliverySignRemark + '\'' +
                '}';
    }
}
