package com.cclean.terminal.vo;

import java.util.List;

/**
 * rfid
 */
public class RfidsVO {

    private List<String> rfids;

    public List<String> getRfids() {
        return rfids;
    }

    public void setRfids(List<String> rfids) {
        this.rfids = rfids;
    }

    @Override
    public String toString() {
        return "RfidsVO{" +
                "rfids=" + rfids +
                '}';
    }
}
