package com.cclean.terminal.vo;

/**
 * @author yulq
 * @create 2018-05-11 9:53
 * @desc 布草袋参数
 **/
public class LinenPackageVO extends PageVO{
    private String id;
    private String code;
    private String color;
    private Integer type;
    private Integer status;
    private String hotelId;
    private String pointId;
    private Integer ltday; //查询小于天数
    private Integer gtday; //查询大于天数

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getLtday() {
        return ltday;
    }

    public void setLtday(Integer ltday) {
        this.ltday = ltday;
    }

    public Integer getGtday() {
        return gtday;
    }

    public void setGtday(Integer gtday) {
        this.gtday = gtday;
    }

    @Override
    public String toString() {
        return "LinenPackageVO{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", color='" + color + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", hotelId='" + hotelId + '\'' +
                ", pointId='" + pointId + '\'' +
                ", ltday=" + ltday +
                ", gtday=" + gtday +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
