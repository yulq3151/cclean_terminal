package com.cclean.terminal.vo;

/**
 * @author yulq
 * @create 2018-05-11 9:53
 * @desc 布草袋参数
 **/
public class LinenPackageVO extends PageVO{
    private String id;
    //编码
    private String code;
    //颜色
    private String color;
    //类型1净 2脏
    private Integer linentype;
    //状态-1报废 1正常
    private Integer linenstate;
    //批次
    private String batch;
    //洗涤次数
    private Integer washNum;
    //使用状态1 入库 2出库
    private Integer usetype;
    //酒店ID
    private String hotelId;
    //配送点ID
    private String pointId;
    //使用者
    private String userId;
    //查询大于天数
    private Integer beginNum;
    //查询小于天数
    private Integer endNum;

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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

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

    public Integer getLinentype() {
        return linentype;
    }

    public void setLinentype(Integer linentype) {
        this.linentype = linentype;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getBeginNum() {
        return beginNum;
    }

    public void setBeginNum(Integer beginNum) {
        this.beginNum = beginNum;
    }

    public Integer getEndNum() {
        return endNum;
    }

    public void setEndNum(Integer endNum) {
        this.endNum = endNum;
    }

    @Override
    public String toString() {
        return "LinenPackageVO{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", color='" + color + '\'' +
                ", linentype=" + linentype +
                ", usetype=" + usetype +
                ", hotelId='" + hotelId + '\'' +
                ", pointId='" + pointId + '\'' +
                ", userId='" + userId + '\'' +
                ", beginNum=" + beginNum +
                ", endNum=" + endNum +
                '}';
    }
}
