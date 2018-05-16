package com.cclean.terminal.vo;

import java.util.List;

/**
 * 只有id参数时公用
 */
public class HotelVO extends PageVO{

    private Integer type;

    private String brandId;

    private String name;

    private List<String> factoryIds;

    public List<String> getFactoryIds() {
        return factoryIds;
    }

    public void setFactoryIds(List<String> factoryIds) {
        this.factoryIds = factoryIds;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HotelVO{" +
                "type=" + type +
                ", brandId='" + brandId + '\'' +
                "} " + super.toString();
    }
}
