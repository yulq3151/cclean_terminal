package com.cclean.terminal.model2;

import java.util.Arrays;

/**
 * @author yulq
 * @create 2018-04-12 13:06
 * @desc
 **/
public class Order extends BaseOrder {
    private static final long serialVersionUID = -7489165956082725347L;
    //配送日期
    private String expectDate;
    //来源单号
    private String fromNo;
    //任务单id
    private String pickOrderNo;
    //订单类型、来源 1送洗收脏2新酒店3酒店下单4差异
    private Integer orderType;
    //0未处理50已处理99终止
    private Integer orderState;
    //0未复核1已复核
    private Integer checkState;
    //布草总数 正常sku数之和
    private Integer totalCnt;
    //未登记数
    private Integer unregisteredCnt;
    //备注
    private String remark;
    //订单的sku详情
    private OrderSku[] skus;
    //酒店
    private HotelBo hotel;
    //配送点
    private DeliveryPointM drypoint;


    public String getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(String expectDate) {
        this.expectDate = expectDate;
    }

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo;
    }

    public String getPickOrderNo() {
        return pickOrderNo;
    }

    public void setPickOrderNo(String pickOrderNo) {
        this.pickOrderNo = pickOrderNo;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    public Integer getUnregisteredCnt() {
        return unregisteredCnt;
    }

    public void setUnregisteredCnt(Integer unregisteredCnt) {
        this.unregisteredCnt = unregisteredCnt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public OrderSku[] getSkus() {
        return skus;
    }

    public void setSkus(OrderSku[] skus) {
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
        return "Order{" +
                "expectDate='" + expectDate + '\'' +
                ", fromNo='" + fromNo + '\'' +
                ", pickOrderNo='" + pickOrderNo + '\'' +
                ", orderType=" + orderType +
                ", orderState=" + orderState +
                ", checkState=" + checkState +
                ", totalCnt=" + totalCnt +
                ", unregisteredCnt=" + unregisteredCnt +
                ", remark='" + remark + '\'' +
                ", skus=" + Arrays.toString(skus) +
                ", hotel=" + hotel +
                ", drypoint=" + drypoint +
                '}';
    }
}
