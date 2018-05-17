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

    private List<LinenPackageStacount> packageStatics;

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

    public List<LinenPackageStacount> getPackageStatics() {
        return packageStatics;
    }

    public void setPackageStatics(List<LinenPackageStacount> packageStatics) {
        this.packageStatics = packageStatics;
    }
}
