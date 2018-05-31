package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 * @author yulq
 * @create 2018-05-31 10:57
 * @desc 库存/浮动的记录
 **/
public class StockRecord implements Serializable{

    private String id;
    //库存ID
    private String stockId;
    //配送点ID
    private String pointId;
    //skuId
    private String skuId;
    //来源单号
    private String fromNo;
    //类型 1库存 2浮动
    private String type;
    //库存调整前
    private String countBefore;
    //库存调整后
    private String countAfter;
    //处理前的浮动数
    private String floatBefore;
    //处理后的浮动数
    private String floatAfter;
    //备注
    private String remark;
    //创建人
    private String creator;
    private String createTime;
    private String modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountBefore() {
        return countBefore;
    }

    public void setCountBefore(String countBefore) {
        this.countBefore = countBefore;
    }

    public String getCountAfter() {
        return countAfter;
    }

    public void setCountAfter(String countAfter) {
        this.countAfter = countAfter;
    }

    public String getFloatBefore() {
        return floatBefore;
    }

    public void setFloatBefore(String floatBefore) {
        this.floatBefore = floatBefore;
    }

    public String getFloatAfter() {
        return floatAfter;
    }

    public void setFloatAfter(String floatAfter) {
        this.floatAfter = floatAfter;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    @Override
    public String toString() {
        return "StockRecord{" +
                "id='" + id + '\'' +
                ", stockId='" + stockId + '\'' +
                ", pointId='" + pointId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", fromNo='" + fromNo + '\'' +
                ", type='" + type + '\'' +
                ", countBefore='" + countBefore + '\'' +
                ", countAfter='" + countAfter + '\'' +
                ", floatBefore='" + floatBefore + '\'' +
                ", floatAfter='" + floatAfter + '\'' +
                ", remark='" + remark + '\'' +
                ", creator='" + creator + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                '}';
    }
}
