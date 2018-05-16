package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 * @author yulq
 * @create 2018-04-12 17:28
 * @desc 酒店的详细信息
 **/
public class HotelM implements Serializable {

    private static final long serialVersionUID = -8365290163946289201L;

    private String id;
    //酒店编号
    private String hotelNo;
    //名称
    private String name;
    //品牌ID
    private String brandId;
    //品牌名称
    private String brandValue;
    //集团ID
    private String groupComp;
    //集团
    private String groupCompValue;
    //地址
    private String address;
    //负责人姓名
    private String managerName;
    //负责人电话
    private String managerTel;
    //楼层总数
    private Integer floorCount;
    //房间总数
    private Integer roomCount;
    //酒店类型1单体2集团
    private Integer hotelType;
    //-1:删除;1:正常;2:停止服务
    private Integer state;
    //酒店所属客户
    private String customer;
    //结算方式 1日结2月结
    private String settlementMode;
    //发货模式 1楼层2总仓
    private String deliveryMode;
    //创建人
    private String operator;
    //创建时间
    private String createTime;
    //修改时间
    private String modifyTime;
    //是否总仓 0否 1是
    private Integer isTotal;
    //收费模式 1洗涤数2配比数总量3房间数4库存数5其他
    private Integer chargeMode;
    // 房间量系数，收费模式为房间数时使用
    private Integer roomCoefficient;
    //是否支持整扎 0不支持1支持
    private Integer isPack;
    //收脏单位id 工厂或布草中心id
    private String scanUnit;
    //收脏单位类型 1工厂2布草中心
    private String scanUnitType;
    //配货单位id 工厂或布草中心id
    private String deliveryUnit;
    //配货单位类型 1工厂2布草中心
    private Integer deliveryUnitType;


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

    public String getGroupComp() {
        return groupComp;
    }

    public void setGroupComp(String groupComp) {
        this.groupComp = groupComp;
    }

    public String getGroupCompValue() {
        return groupCompValue;
    }

    public void setGroupCompValue(String groupCompValue) {
        this.groupCompValue = groupCompValue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerTel() {
        return managerTel;
    }

    public void setManagerTel(String managerTel) {
        this.managerTel = managerTel;
    }

    public Integer getFloorCount() {
        return floorCount;
    }

    public void setFloorCount(Integer floorCount) {
        this.floorCount = floorCount;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public Integer getHotelType() {
        return hotelType;
    }

    public void setHotelType(Integer hotelType) {
        this.hotelType = hotelType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSettlementMode() {
        return settlementMode;
    }

    public void setSettlementMode(String settlementMode) {
        this.settlementMode = settlementMode;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public Integer getIsTotal() {
        return isTotal;
    }

    public void setIsTotal(Integer isTotal) {
        this.isTotal = isTotal;
    }

    public Integer getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(Integer chargeMode) {
        this.chargeMode = chargeMode;
    }

    public Integer getRoomCoefficient() {
        return roomCoefficient;
    }

    public void setRoomCoefficient(Integer roomCoefficient) {
        this.roomCoefficient = roomCoefficient;
    }

    public Integer getIsPack() {
        return isPack;
    }

    public void setIsPack(Integer isPack) {
        this.isPack = isPack;
    }

    public String getScanUnit() {
        return scanUnit;
    }

    public void setScanUnit(String scanUnit) {
        this.scanUnit = scanUnit;
    }

    public String getScanUnitType() {
        return scanUnitType;
    }

    public void setScanUnitType(String scanUnitType) {
        this.scanUnitType = scanUnitType;
    }

    public String getDeliveryUnit() {
        return deliveryUnit;
    }

    public void setDeliveryUnit(String deliveryUnit) {
        this.deliveryUnit = deliveryUnit;
    }

    public Integer getDeliveryUnitType() {
        return deliveryUnitType;
    }

    public void setDeliveryUnitType(Integer deliveryUnitType) {
        this.deliveryUnitType = deliveryUnitType;
    }

    @Override
    public String toString() {
        return "HotelM{" +
                "id='" + id + '\'' +
                ", hotelNo='" + hotelNo + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brandId + '\'' +
                ", brandValue='" + brandValue + '\'' +
                ", groupComp='" + groupComp + '\'' +
                ", groupCompValue='" + groupCompValue + '\'' +
                ", address='" + address + '\'' +
                ", managerName='" + managerName + '\'' +
                ", managerTel='" + managerTel + '\'' +
                ", floorCount=" + floorCount +
                ", roomCount=" + roomCount +
                ", hotelType=" + hotelType +
                ", state=" + state +
                ", customer='" + customer + '\'' +
                ", settlementMode='" + settlementMode + '\'' +
                ", deliveryMode='" + deliveryMode + '\'' +
                ", operator='" + operator + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", isTotal=" + isTotal +
                ", chargeMode=" + chargeMode +
                ", roomCoefficient=" + roomCoefficient +
                ", isPack=" + isPack +
                ", scanUnit='" + scanUnit + '\'' +
                ", scanUnitType='" + scanUnitType + '\'' +
                ", deliveryUnit='" + deliveryUnit + '\'' +
                ", deliveryUnitType=" + deliveryUnitType +
                '}';
    }
}
