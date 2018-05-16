package com.cclean.terminal.model;

/**
 * Sku统计信息
 */
public class SkuStatistics {

    // sku
    private Sku sku;
    // sku数量
    private Integer count;
    // 实际数量
    private Integer actualCount;
    // 差异数量
    private Integer differenceCount;

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

    public Integer getActualCount() {
        return actualCount;
    }

    public void setActualCount(Integer actualCount) {
        this.actualCount = actualCount;
    }

    public Integer getDifferenceCount() {
        return differenceCount;
    }

    public void setDifferenceCount(Integer differenceCount) {
        this.differenceCount = differenceCount;
    }

    @Override
    public String toString() {
        return "SkuStatistics{" +
                "sku=" + sku +
                ", count=" + count +
                ", actualCount=" + actualCount +
                ", differenceCount=" + differenceCount +
                '}';
    }
}
