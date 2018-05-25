package com.cclean.terminal.model2;

import java.util.Arrays;
import java.util.List;

/**
 *  配送单
 * @author yulq
 * @create 2018-04-12 13:08
 * @desc
 **/
public class DeliveryOrder extends BaseOrder {
    private static final long serialVersionUID = 1015604690043896825L;
    //任务单id
    private String pickOrderId;
    //提（发）货时间
    private String takeTime;
    //签收时间
    private String signTime;
    //签收备注
    private String signDesc;
    //配送单状态 1未处理50已发货99已签收
    private Integer state;
    //送货员电话
    private String deliveryTel;
    //送货员
    private String deliveryMan;
    //配送单sku详情
    private DeliveryOrderSku[] skus;
    //酒店
    private HotelBo hotel;
    //配送点
    private DeliveryPointM drypoint;
    //配送单sku总数 实配数之和
    private Integer totalCnt;

    private List<DeliveryOrderPackage> packages;

    public List<DeliveryOrderPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<DeliveryOrderPackage> packages) {
        this.packages = packages;
    }

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    public String getPickOrderId() {
        return pickOrderId;
    }

    public void setPickOrderId(String pickOrderId) {
        this.pickOrderId = pickOrderId;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getSignDesc() {
        return signDesc;
    }

    public void setSignDesc(String signDesc) {
        this.signDesc = signDesc;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDeliveryTel() {
        return deliveryTel;
    }

    public void setDeliveryTel(String deliveryTel) {
        this.deliveryTel = deliveryTel;
    }

    public String getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(String deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public DeliveryOrderSku[] getSkus() {
        return skus;
    }

    public void setSkus(DeliveryOrderSku[] skus) {
        this.skus = skus;
    }

    public HotelBo getHotel() {
        return hotel;
    }

    public void setHotel(HotelBo hotel) {
        this.hotel = hotel;
    }

    public DeliveryPointM getDrypoint() {
        return drypoint;
    }

    public void setDrypoint(DeliveryPointM drypoint) {
        this.drypoint = drypoint;
    }

    @Override
    public String toString() {
        return "DeliveryOrder{" +
                "pickOrderId='" + pickOrderId + '\'' +
                ", takeTime='" + takeTime + '\'' +
                ", signTime='" + signTime + '\'' +
                ", signDesc='" + signDesc + '\'' +
                ", state=" + state +
                ", deliveryTel='" + deliveryTel + '\'' +
                ", deliveryMan='" + deliveryMan + '\'' +
                ", skus=" + Arrays.toString(skus) +
                '}';
    }
}
