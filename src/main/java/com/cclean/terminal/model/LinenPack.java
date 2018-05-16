package com.cclean.terminal.model;

import java.util.Date;
import java.util.List;

/**
 * 布草打扎信息
 */
public class LinenPack {

    // 打扎编码
    private String code;
    // Sku信息
    private Sku sku;
    // 数量
    private Integer count;
    // 芯片码集合
    private List<String> rfids;

    private String factoryId;

    private String factoryName;

    private String creator;

    private Date createTime;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<String> getRfids() {
        return rfids;
    }

    public void setRfids(List<String> rfids) {
        this.rfids = rfids;
    }

    @Override
    public String toString() {
        return "LinenPack{" +
                "code='" + code + '\'' +
                ", sku=" + sku +
                ", count=" + count +
                ", rfids=" + rfids +
                '}';
    }
}
