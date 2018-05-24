package com.cclean.terminal.model2;

import java.io.Serializable;
import java.util.List;

/**
 * @author yulq
 * @create 2018-05-16 13:09
 * @desc 布草袋的报表
 **/
public class LinenPackageStatistics implements Serializable{

    //酒店ID
    private String hotelId;
    //酒店名称
    private String hotelName;

    private List<LinenPackageStacount> hotelData;

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

    public List<LinenPackageStacount> getHotelData() {
        return hotelData;
    }

    public void setHotelData(List<LinenPackageStacount> hotelData) {
        this.hotelData = hotelData;
    }
}
