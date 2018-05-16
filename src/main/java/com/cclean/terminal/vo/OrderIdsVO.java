package com.cclean.terminal.vo;

import java.util.List;

/**
 * rfid
 */
public class OrderIdsVO {

    private List<String> orderIds;

    public List<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<String> orderIds) {
        this.orderIds = orderIds;
    }

    @Override
    public String toString() {
        return "OrderIdsVO{" +
                "orderIds=" + orderIds +
                '}';
    }

}
