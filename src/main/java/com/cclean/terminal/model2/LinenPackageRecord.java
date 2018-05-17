package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 * @author yulq
 * @create 2018-05-16 11:30
 * @desc
 **/
public class LinenPackageRecord implements Serializable {

    private String id;
    //布草袋id
    private String packageId;
    //布草袋编码
    private String code;
    //酒店ID
    private String hotelId;
    //酒店名称
    private String hotelName;
    //配送点ID
    private String pointId;
    //配送点名称
    private String pointName;
    //信用时间
    private String receiveTime;
    //归还时间
    private String returnTime;
    //使用人
    private String creator;
    //使用人姓名
    private String creatorName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
