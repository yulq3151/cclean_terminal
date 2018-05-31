package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 * @author yulq
 * @create 2018-05-23 11:40
 * @desc 笼车
 **/
public class Cagecar implements Serializable {
    //笼车ID
    private String id;
    //笼车code
    private String code;
    //所属工厂
    private String factoryId;
    private String createTime;
    private String modifyTime;

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

    @Override
    public String toString() {
        return "Cagecar{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", factoryId='" + factoryId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                '}';
    }
}
