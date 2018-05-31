package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 *  打扎信息
 * @author yulq
 * @create 2018-04-12 22:31
 * @desc
 **/
public class LinenPackM implements Serializable {
    private static final long serialVersionUID = 4713581545988198708L;
    //编号
    private String id;
    //编号
    private String code;
    //sku
    private String skuId;
    //sku名称
    private String skuName;
    //数量
    private Integer count;
    //状态 0正常1删除
    private Integer state;
    //创建人ID
    private String creator;
    private String createTime;
    private String modifyTime;
    //芯片ID
    private String[] rfids;

    public String[] getRfids() {
        return rfids;
    }

    public void setRfids(String[] rfids) {
        this.rfids = rfids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
        return "LinenPackM{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", skuId='" + skuId + '\'' +
                ", count=" + count +
                ", state=" + state +
                ", creator='" + creator + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                '}';
    }
}
