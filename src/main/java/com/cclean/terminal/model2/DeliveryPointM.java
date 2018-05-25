package com.cclean.terminal.model2;

import java.io.Serializable;

/** 配送点
 * @author yulq
 * @create 2018-04-13 9:53
 * @desc
 **/
public class DeliveryPointM implements Serializable {

    //
    private String id;
    //名称
    private String name;
    //酒店ID
    private String hotelId;
    //是否是集团，0位集团，1位单体
    private Integer isGroup;
    //楼层
    private String floorNum;
    //楼层区域
    private String floorArea;
    //位置
    private String position;
    //房间数量
    private Integer roomCount;
    //有无未处理的订单和任务单 0无1有
    private Integer status;
//    //负责人
//    private String managerName;
//    //负责人电话
//    private String managerTel;
//    //创建人
//    private String creator;
//    //
//    private String createTime;
//    //
//    private String modifyTime ;
//    //描述
//    private String descript;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Integer isGroup) {
        this.isGroup = isGroup;
    }

    public String getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(String floorNum) {
        this.floorNum = floorNum;
    }

    public String getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(String floorArea) {
        this.floorArea = floorArea;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    //
//    public String getManagerName() {
//        return managerName;
//    }
//
//    public void setManagerName(String managerName) {
//        this.managerName = managerName;
//    }
//
//    public String getManagerTel() {
//        return managerTel;
//    }
//
//    public void setManagerTel(String managerTel) {
//        this.managerTel = managerTel;
//    }
//
//    public String getCreator() {
//        return creator;
//    }
//
//    public void setCreator(String creator) {
//        this.creator = creator;
//    }
//
//    public String getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(String createTime) {
//        this.createTime = createTime;
//    }
//
//    public String getModifyTime() {
//        return modifyTime;
//    }
//
//    public void setModifyTime(String modifyTime) {
//        this.modifyTime = modifyTime;
//    }
//
//    public String getDescript() {
//        return descript;
//    }
//
//    public void setDescript(String descript) {
//        this.descript = descript;
//    }
}
