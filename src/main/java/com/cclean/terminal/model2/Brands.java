package com.cclean.terminal.model2;

import java.io.Serializable;

/** 品牌
 * @author yulq
 * @create 2018-04-12 20:04
 * @desc
 **/
public class Brands implements Serializable {


    private static final long serialVersionUID = 865846103166362121L;


    private String id;
    //集团ID
    private String groupId;
    //集团
    private String groupName;
    //品牌编号
    private String code;
    //品牌名称
    private String name;
    //创建者
    private String creator;
    private String createTime;
    private String modifyTime ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Brands{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", code='" + code + '\'' +
                ", name=" + name +
                ", creator='" + creator + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                '}';
    }
}
