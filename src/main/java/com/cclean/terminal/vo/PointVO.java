package com.cclean.terminal.vo;

/**
 * Created by Administrator on 2018/4/8.
 */
public class PointVO extends PageVO{

    private String hotelId;

    public PointVO(){

    }

    public PointVO(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public String toString() {
        return "PointVO{" +
                "hotelId='" + hotelId + '\'' +
                "} " + super.toString();
    }
}
