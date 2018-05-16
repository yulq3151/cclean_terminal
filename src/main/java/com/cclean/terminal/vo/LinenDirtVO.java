package com.cclean.terminal.vo;

import com.cclean.terminal.model.SkuStatistics;
import com.cclean.terminal.model.SkuStatisticsCopy;

import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 */
public class LinenDirtVO {

    private List<String> rfids;
    private String deliveryPointId;
    private List<SkuStatisticsCopy> skus;

    public List<String> getRfids() {
        return rfids;
    }

    public void setRfids(List<String> rfids) {
        this.rfids = rfids;
    }

    public String getDeliveryPointId() {
        return deliveryPointId;
    }

    public void setDeliveryPointId(String deliveryPointId) {
        this.deliveryPointId = deliveryPointId;
    }

    public List<SkuStatisticsCopy> getSkus() {
        return skus;
    }

    public void setSkus(List<SkuStatisticsCopy> skus) {
        this.skus = skus;
    }
    @Override
    public String toString() {
        return "LinenDirtVO{" +
                "rfids=" + rfids +
                ", deliveryPointId='" + deliveryPointId + '\'' +
                ", skus=" + skus +
                '}';
    }
}
