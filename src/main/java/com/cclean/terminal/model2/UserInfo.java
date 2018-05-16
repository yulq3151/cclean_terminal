package com.cclean.terminal.model2;

import java.io.Serializable;
import java.util.List;

/**
 * @author yulq
 * @create 2018-04-19 21:19
 * @desc 用户信息
 **/
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 2912852302926614894L;
    private String id;
    private String name;
    private String phone;
    private String realName;
    private String status;
    private List<String> factoryid;
//    private List<String> menus;
    private List<AuthorityDetailList> auths;

//    public List<String> getMenus() {
//        return menus;
//    }
//
//    public void setMenus(List<String> menus) {
//        this.menus = menus;
//    }

    public List<String> getFactoryid() {
        return factoryid;
    }

    public void setFactoryid(List<String> factoryid) {
        this.factoryid = factoryid;
    }


    public List<AuthorityDetailList> getAuths() {
        return auths;
    }

    public void setAuths(List<AuthorityDetailList> auths) {
        this.auths = auths;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
