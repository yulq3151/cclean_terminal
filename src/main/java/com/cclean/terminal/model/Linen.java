package com.cclean.terminal.model;

import java.util.Date;

/**
 * 布草信息
 */
public class Linen {

    // 布草ID号
    private String id;
    // 电子标签编号
    private String rfid;
    // 对应的sku
    private Sku sku;
    // 等级
    private String level;
    // 最后使用的酒店ID
    private String lastHotel;
    // 最后洗涤的工厂ID
    private String lastFactory;
    // 洗涤次数
    private Integer washCount;
    // 供应商信息
    private Supplier supplier;
    // 产权所有者信息
    private Property property;
    // 批次号
    private String batch;
    //收货状态
    private Integer receiveState;
    //收货时间
    private Date receiveTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLastHotel() {
        return lastHotel;
    }

    public void setLastHotel(String lastHotel) {
        this.lastHotel = lastHotel;
    }

    public String getLastFactory() {
        return lastFactory;
    }

    public void setLastFactory(String lastFactory) {
        this.lastFactory = lastFactory;
    }

    public Integer getWashCount() {
        return washCount;
    }

    public void setWashCount(Integer washCount) {
        this.washCount = washCount;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getReceiveState() {
        return receiveState;
    }

    public void setReceiveState(Integer receiveState) {
        this.receiveState = receiveState;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    @Override
    public String toString() {
        return "Linen{" +
                "id='" + id + '\'' +
                ", rfid='" + rfid + '\'' +
                ", sku=" + sku +
                ", level='" + level + '\'' +
                ", lastHotel='" + lastHotel + '\'' +
                ", lastFactory='" + lastFactory + '\'' +
                ", washCount=" + washCount +
                ", supplier=" + supplier +
                ", property=" + property +
                ", batch='" + batch + '\'' +
                ", receiveState=" + receiveState +
                ", receiveTime=" + receiveTime +
                '}';
    }
}
