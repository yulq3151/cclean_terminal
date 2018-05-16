package com.cclean.terminal.vo;

import java.util.Arrays;

/**
 * @author yulq
 * @create 2018-04-13 14:38
 * @desc
 **/
public class ZPickVo {
    //任务单ID
    private String workOrderId;
    //sku统计信息
    private SkuSVo[] skuSVos;
    //布草
    private String[] rfids;
    //打扎ID
    private String[] packids;

    public String[] getPackids() {
        return packids;
    }

    public void setPackids(String[] packids) {
        this.packids = packids;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public SkuSVo[] getSkuSVos() {
        return skuSVos;
    }

    public void setSkuSVos(SkuSVo[] skuSVos) {
        this.skuSVos = skuSVos;
    }

    public String[] getRfids() {
        return rfids;
    }

    public void setRfids(String[] rfids) {
        this.rfids = rfids;
    }

    @Override
    public String toString() {
        return "ZPickVo{" +
                "workOrderId='" + workOrderId + '\'' +
                ", skuSVos=" + Arrays.toString(skuSVos) +
                ", rfids=" + Arrays.toString(rfids) +
                ", packids=" + Arrays.toString(packids) +
                '}';
    }
}
