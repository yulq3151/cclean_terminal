package com.cclean.terminal.model2;

import java.util.List;

/**
 * @author yulq
 * @create 2018-04-12 13:11
 * @desc 任务单
 **/
public class PickorderOrder extends BaseOrder {

    private static final long serialVersionUID = -5016421134380362464L;
    //配送单id
    private String devliveryOrderId;
    //任务单状态
    private Integer state;
    //应配数量
    private Integer expectCount;
    //实配数量
    private Integer realCount;
    //sku信息
    private List<PickOrderSku> skus;
    //酒店
    private HotelBo hotel;
    //配送点
    private DeliveryPointM drypoint;
    //sku应配数量
    private Integer totalCnt;

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    public String getDevliveryOrderId() {
        return devliveryOrderId;
    }

    public void setDevliveryOrderId(String devliveryOrderId) {
        this.devliveryOrderId = devliveryOrderId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getExpectCount() {
        return expectCount;
    }

    public void setExpectCount(Integer expectCount) {
        this.expectCount = expectCount;
    }

    public Integer getRealCount() {
        return realCount;
    }

    public void setRealCount(Integer realCount) {
        this.realCount = realCount;
    }

    public List<PickOrderSku> getSkus() {
        return skus;
    }

    public void setSkus(List<PickOrderSku> skus) {
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
        return "PickorderOrder{" +
                "devliveryOrderId='" + devliveryOrderId + '\'' +
                ", state=" + state +
                ", expectCount=" + expectCount +
                ", realCount=" + realCount +
                ", skus=" + skus +
                ", hotel=" + hotel +
                ", drypoint=" + drypoint +
                ", totalCnt=" + totalCnt +
                '}';
    }
}
