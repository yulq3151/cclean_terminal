package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 * @author yulq
 * @create 2018-04-16 16:07
 * @desc 酒店线路
 **/
public class Hoteline implements Serializable{


    private static final long serialVersionUID = -8400129235235041172L;
    //酒店ID
    private String hotelId;
    //酒店名称
    private String hotelName;
    //序号
    private Integer orderNum;
    //属于哪条线路ID
    private String deliveryLineId;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getDeliveryLineId() {
        return deliveryLineId;
    }

    public void setDeliveryLineId(String deliveryLineId) {
        this.deliveryLineId = deliveryLineId;
    }

    @Override
    public String toString() {
        return "Hoteline{" +
                "hotelId='" + hotelId + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", deliveryLineId='" + deliveryLineId + '\'' +
                '}';
    }
}
