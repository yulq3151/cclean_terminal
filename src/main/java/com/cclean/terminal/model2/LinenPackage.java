package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 * @author yulq
 * @create 2018-05-10 17:45
 * @desc 布草包
 **/
public class LinenPackage implements Serializable{

    private String id;
    //编码
    private String code;
    //编码
    private String rfid;
    //颜色
    private String color;
    //批次号
    private String batch;
    //类型 1净 2脏
    private Integer linentype;
    //状态-1:报废;1:正常
    private Integer linenstate;
    //洗涤次数
    private Integer washNum;
    //使用状态 1入库2出库
    private Integer usetype;
    //酒店id
    private String hotelId;
    //酒店名称
    private String hotelName;
    //配送点id
    private String pointId;
    //配送点名称
    private String pointName;
    //领取时间
    private String receiveTime;
    //归还时间
    private String returnTime;
    //使用人
    private String userId;
    //使用人姓名
        private String userName;
    //操作人
    private String creator;
    //操作人姓名
    private String creatorName;

    private String createTime;
    private String modifyTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getLinentype() {
        return linentype;
    }

    public void setLinentype(Integer linentype) {
        this.linentype = linentype;
    }

    public Integer getLinenstate() {
        return linenstate;
    }

    public void setLinenstate(Integer linenstate) {
        this.linenstate = linenstate;
    }

    public Integer getWashNum() {
        return washNum;
    }

    public void setWashNum(Integer washNum) {
        this.washNum = washNum;
    }

    public Integer getUsetype() {
        return usetype;
    }

    public void setUsetype(Integer usetype) {
        this.usetype = usetype;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
