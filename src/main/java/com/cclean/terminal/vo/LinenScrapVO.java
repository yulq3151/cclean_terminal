package com.cclean.terminal.vo;

import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 */
public class LinenScrapVO {

    private List<String> rfids;
    private String responsiId;
    private String responsibility;
    private String scrapReason;

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

    public String getScrapReason() {
        return scrapReason;
    }

    public void setScrapReason(String scrapReason) {
        this.scrapReason = scrapReason;
    }

    @Override
    public String toString() {
        return "LinenScrapVO{" +
                "rfids=" + rfids +
                ", responsiId='" + responsiId + '\'' +
                ", responsibility='" + responsibility + '\'' +
                ", scrapReason='" + scrapReason + '\'' +
                '}';
    }
}
