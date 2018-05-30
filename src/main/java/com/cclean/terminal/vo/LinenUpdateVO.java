package com.cclean.terminal.vo;

import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 */
public class LinenUpdateVO {

    private List<String> rfids;
    private int type;
    private String skuId;
    private String supplierId;
    private String propertyId;
    private String level;
    private String batch;

    public List<String> getRfids() {
        return rfids;
    }

    public void setRfids(List<String> rfids) {
        this.rfids = rfids;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "LinenUpdateVO{" +
                "rfids=" + rfids +
                ", type=" + type +
                ", skuId='" + skuId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", level='" + level + '\'' +
                ", batch='" + batch + '\'' +
                '}';
    }
}
