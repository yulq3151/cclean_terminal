package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 * @author yulq
 * @create 2018-04-13 20:09
 * @desc 酒店的简易信息
 **/
public class HotelBo implements Serializable {
    private String id;
    //酒店编号
    private String hotelNo;
    //名称
    private String name;
    //品牌ID
    private String brandId;
    //品牌
    private String brandValue;
    //地址
    private String address;
//    //创建时间
//    private String createTime;
//    //修改时间
//    private String modifyTime;
    //是否总仓 0否 1是
    private Integer isTotal;


    public Integer getIsTotal() {
        return isTotal;
    }

    public void setIsTotal(Integer isTotal) {
        this.isTotal = isTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelNo() {
        return hotelNo;
    }

    public void setHotelNo(String hotelNo) {
        this.hotelNo = hotelNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandValue() {
        return brandValue;
    }

    public void setBrandValue(String brandValue) {
        this.brandValue = brandValue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    @Override
    public String toString() {
        return "HotelBo{" +
                "id='" + id + '\'' +
                ", hotelNo='" + hotelNo + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brandId + '\'' +
                ", brandValue='" + brandValue + '\'' +
                ", address='" + address + '\'' +
//                ", createTime='" + createTime + '\'' +
//                ", modifyTime='" + modifyTime + '\'' +
                ", isTotal=" + isTotal +
                '}';
    }
}
