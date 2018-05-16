package com.cclean.terminal.model;

/**
 * @author yulq
 * @create 2018-04-11 15:58
 * @desc
 **/
public class Deliveryline {
    //主键
    private String id;
    //名称
    private String name;
    //开始点
    private String start;
    //最终点
    private String terminus;
    //优先级 数字越小，优先级越高
    private String priority;
    //创建时间
    private String createTime;
    //修改时间
    private String modifyTime;
    //所有酒店
    private String hotels;
    //状态 -1停用 1启用
    private String state;

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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getTerminus() {
        return terminus;
    }

    public void setTerminus(String terminus) {
        this.terminus = terminus;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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

    public String getHotels() {
        return hotels;
    }

    public void setHotels(String hotels) {
        this.hotels = hotels;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
