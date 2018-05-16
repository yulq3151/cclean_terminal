package com.cclean.terminal.entity;

import java.util.Date;

public class TLinen {

    private String id;

    private String rfidId;

    private String skuId;

    private String supplierId;

    private String propertyOwnerId;

    private String containerCode;

    private Integer linenState;

    private Integer transferState;

    private String batch;

    private Integer washTime;

    private String level;

    private String lastHotel;

    private String lastPoint;

    private String lastFactory;

    private Boolean isrewash;

    private Boolean isInternalRewash;

    private String creator;

    private Date fristOutgoTime;

    private Date createTime;

    private Date modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRfidId() {
        return rfidId;
    }

    public void setRfidId(String rfidId) {
        this.rfidId = rfidId == null ? null : rfidId.trim();
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId == null ? null : skuId.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getPropertyOwnerId() {
        return propertyOwnerId;
    }

    public void setPropertyOwnerId(String propertyOwnerId) {
        this.propertyOwnerId = propertyOwnerId == null ? null : propertyOwnerId.trim();
    }

    public String getContainerCode() {
        return containerCode;
    }

    public void setContainerCode(String containerCode) {
        this.containerCode = containerCode == null ? null : containerCode.trim();
    }

    public Integer getLinenState() {
        return linenState;
    }

    public void setLinenState(Integer linenState) {
        this.linenState = linenState;
    }

    public Integer getTransferState() {
        return transferState;
    }

    public void setTransferState(Integer transferState) {
        this.transferState = transferState;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch == null ? null : batch.trim();
    }

    public Integer getWashTime() {
        return washTime;
    }

    public void setWashTime(Integer washTime) {
        this.washTime = washTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getLastHotel() {
        return lastHotel;
    }

    public void setLastHotel(String lastHotel) {
        this.lastHotel = lastHotel == null ? null : lastHotel.trim();
    }

    public String getLastPoint() {
        return lastPoint;
    }

    public void setLastPoint(String lastPoint) {
        this.lastPoint = lastPoint == null ? null : lastPoint.trim();
    }

    public String getLastFactory() {
        return lastFactory;
    }

    public void setLastFactory(String lastFactory) {
        this.lastFactory = lastFactory == null ? null : lastFactory.trim();
    }

    public Boolean getIsrewash() {
        return isrewash;
    }

    public void setIsrewash(Boolean isrewash) {
        this.isrewash = isrewash;
    }

    public Boolean getIsInternalRewash() {
        return isInternalRewash;
    }

    public void setIsInternalRewash(Boolean isInternalRewash) {
        this.isInternalRewash = isInternalRewash;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getFristOutgoTime() {
        return fristOutgoTime;
    }

    public void setFristOutgoTime(Date fristOutgoTime) {
        this.fristOutgoTime = fristOutgoTime;
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
}