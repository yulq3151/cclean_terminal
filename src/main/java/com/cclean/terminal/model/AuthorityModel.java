package com.cclean.terminal.model;

import com.cclean.terminal.entity.TMenu;

import java.util.List;

public class AuthorityModel {

    //角色菜单
    private TMenu roleMenu;

    //用户权限
    private List<AuthorityDetail> authorityDetailList;

    public TMenu getRoleMenu() {
        return roleMenu;
    }

    public void setRoleMenu(TMenu roleMenu) {
        this.roleMenu = roleMenu;
    }

    public List<AuthorityDetail> getAuthorityDetailList() {
        return authorityDetailList;
    }

    public void setAuthorityDetailList(List<AuthorityDetail> authorityDetailList) {
        this.authorityDetailList = authorityDetailList;
    }

    @Override
    public String toString() {
        return "AuthorityModel{" +
                "roleMenu=" + roleMenu +
                ", authorityDetailList=" + authorityDetailList +
                '}';
    }
}
