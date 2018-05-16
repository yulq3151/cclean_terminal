package com.cclean.terminal.model2;

import java.io.Serializable;
import java.util.Arrays;

/**
 *  配送线路
 * @author yulq
 * @create 2018-04-12 21:16
 * @desc
 **/
public class DeliverylineM implements Serializable {

    private static final long serialVersionUID = -1414717890447774321L;

    private String id;
    //线路名称
    private String name;
    //开始酒店名称
    private String start;
    //结束酒店名称
    private String terminus;
    //状态 -1停用 1启用
    private String state;
    //优先级 从0开始的整数，数字越小优先级越高
    private String priority;
    private String create_time;
    private String modify_time;

    private Hoteline[] hotels;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    public Hoteline[] getHotels() {
        return hotels;
    }

    public void setHotels(Hoteline[] hotels) {
        this.hotels = hotels;
    }

    @Override
    public String toString() {
        return "DeliverylineM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", start='" + start + '\'' +
                ", terminus='" + terminus + '\'' +
                ", state='" + state + '\'' +
                ", priority='" + priority + '\'' +
                ", create_time='" + create_time + '\'' +
                ", modify_time='" + modify_time + '\'' +
                ", hotels=" + Arrays.toString(hotels) +
                '}';
    }
}
