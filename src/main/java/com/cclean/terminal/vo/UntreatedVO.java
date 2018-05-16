package com.cclean.terminal.vo;

/**
 * Created by hubin on 2018/3/23.
 */
public class UntreatedVO {

    private String pointId;
    private String hotelId;

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public String toString() {
        return "UntreatedVO{" +
                "pointId='" + pointId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                '}';
    }
}
