package com.cclean.terminal.model;

/**
 * 配送点
 */
public class DeliveryPoint {

    private String id;
    private String floor;
    //有无订单/任务单 0无1有
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "DeliveryPoint{" +
                "id='" + id + '\'' +
                ", floor='" + floor + '\'' +
                '}';
    }
}
