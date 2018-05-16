package com.cclean.terminal.model;


import java.util.Map;

/**
 * 用户权限信息 model
 */
public class UserAuthority {

    private String id;

    private String name;

    private String phone;

    private String realName;

    private Map<String, AuthorityModel> authorityInfo;

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

    public Map<String, AuthorityModel> getAuthorityInfo() {
        return authorityInfo;
    }

    public void setAuthorityInfo(Map<String, AuthorityModel> authorityInfo) {
        this.authorityInfo = authorityInfo;
    }

    @Override
    public String toString() {
        return "UserAuthority{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", realName='" + realName + '\'' +
                ", authorityInfo=" + authorityInfo +
                '}';
    }
}
