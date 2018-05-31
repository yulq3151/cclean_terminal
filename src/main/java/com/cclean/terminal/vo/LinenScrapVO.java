package com.cclean.terminal.vo;

import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 */
public class LinenScrapVO {

    private List<String> rfids;
    //责任方类型：0酒店1工厂2供应商3物流4超洁
    private String responsibility;
    //责任方对应的ID
    private String responsiId;
    //报废原因ID
    private String scrapReasonId;

    public List<String> getRfids() {
        return rfids;
    }

    public void setRfids(List<String> rfids) {
        this.rfids = rfids;
    }

    public String getResponsiId() {
        return responsiId;
    }

    public void setResponsiId(String responsiId) {
        this.responsiId = responsiId;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getScrapReasonId() {
        return scrapReasonId;
    }

    public void setScrapReasonId(String scrapReasonId) {
        this.scrapReasonId = scrapReasonId;
    }

    @Override
    public String toString() {
        return "LinenScrapVO{" +
                "rfids=" + rfids +
                ", responsiId='" + responsiId + '\'' +
                ", responsibility='" + responsibility + '\'' +
                ", scrapReasonId='" + scrapReasonId + '\'' +
                '}';
    }
}
