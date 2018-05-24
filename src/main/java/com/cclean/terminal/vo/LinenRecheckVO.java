package com.cclean.terminal.vo;

import com.cclean.terminal.model.SkuStatisticsCopy;

import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 */
public class LinenRecheckVO {

    private List<String> rfids;
    private List<SkuStatisticsCopy> skuStatisticss;
    private List<String> basiss;

    public List<String> getRfids() {
        return rfids;
    }

    public void setRfids(List<String> rfids) {
        this.rfids = rfids;
    }

    public List<SkuStatisticsCopy> getSkuStatisticss() {
        return skuStatisticss;
    }

    public void setSkuStatisticss(List<SkuStatisticsCopy> skuStatisticss) {
        this.skuStatisticss = skuStatisticss;
    }

    public List<String> getBasiss() {
        return basiss;
    }

    public void setBasiss(List<String> basiss) {
        this.basiss = basiss;
    }

    @Override
    public String toString() {
        return "LinenRecheckVO{" +
                "rfids=" + rfids +
                ", skuStatisticss=" + skuStatisticss +
                ", basiss=" + basiss +
                '}';
    }
}
