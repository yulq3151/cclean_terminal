package com.cclean.terminal.model;

/**
 * Created by hubin on 2018/3/24.
 * 报废原因
 */
public class ScrapReason {

    //id
    private String id;
    //报废原因
    private String scrapReason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScrapReason() {
        return scrapReason;
    }

    public void setScrapReason(String scrapReason) {
        this.scrapReason = scrapReason;
    }

    @Override
    public String toString() {
        return "ScrapReason{" +
                "id='" + id + '\'' +
                ", scrapReason='" + scrapReason + '\'' +
                '}';
    }
}
