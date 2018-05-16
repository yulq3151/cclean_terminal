package com.cclean.terminal.vo;

import com.cclean.terminal.model.SkuStatistics;
import com.cclean.terminal.model.SkuStatisticsCopy;

import java.util.List;

/**
 * rfid
 */
public class GenerateVO {

    private List<String> rfids;
    private String workOrderId;
    private List<SkuStatisticsCopy> skuStatisticss;

    public List<String> getRfids() {
        return rfids;
    }

    public void setRfids(List<String> rfids) {
        this.rfids = rfids;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public List<SkuStatisticsCopy> getSkuStatisticss() {
        return skuStatisticss;
    }

    public void setSkuStatisticss(List<SkuStatisticsCopy> skuStatisticss) {
        this.skuStatisticss = skuStatisticss;
    }

    @Override
    public String toString() {
        return "GenerateVO{" +
                "rfids=" + rfids +
                ", workOrderId='" + workOrderId + '\'' +
                ", skuStatisticss=" + skuStatisticss +
                '}';
    }
}
