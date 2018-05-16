package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 * 订单的基础属性
 * @author yulq
 * @create 2018-04-12 13:06
 * @desc
 **/
public class BaseOrder implements Serializable{
    private static final long serialVersionUID = 6142375974087132048L;
    private String id;
    private String hotelId;
    private String pointId;
    private String factoryId;
    private String createTime;
    private String modifyTime;
    //操作人ID
    private String operator;
    //操作人姓名
    private String operatorName;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
