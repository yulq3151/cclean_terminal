package com.cclean.terminal.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author yulq
 * @create 2018-05-22 10:23
 * @desc
 **/
public class SkuReceived implements Serializable {

    //未登记rfid
    private List<String> unregisteredList;
    //已收脏rfid
    private List<String> receivedList;
    //未收脏rfid（正常）
    private List<String> unReceivedList;
    //已收脏sku信息
    private List<SkuStatistics> receskuStatisticsList;
    //未收脏sku信息
    private List<SkuStatistics> unReceskuStatisticsList;

    public List<String> getUnregisteredList() {
        return unregisteredList;
    }

    public void setUnregisteredList(List<String> unregisteredList) {
        this.unregisteredList = unregisteredList;
    }

    public List<String> getReceivedList() {
        return receivedList;
    }

    public void setReceivedList(List<String> receivedList) {
        this.receivedList = receivedList;
    }

    public List<String> getUnReceivedList() {
        return unReceivedList;
    }

    public void setUnReceivedList(List<String> unReceivedList) {
        this.unReceivedList = unReceivedList;
    }

    public List<SkuStatistics> getReceskuStatisticsList() {
        return receskuStatisticsList;
    }

    public void setReceskuStatisticsList(List<SkuStatistics> receskuStatisticsList) {
        this.receskuStatisticsList = receskuStatisticsList;
    }

    public List<SkuStatistics> getUnReceskuStatisticsList() {
        return unReceskuStatisticsList;
    }

    public void setUnReceskuStatisticsList(List<SkuStatistics> unReceskuStatisticsList) {
        this.unReceskuStatisticsList = unReceskuStatisticsList;
    }
}
