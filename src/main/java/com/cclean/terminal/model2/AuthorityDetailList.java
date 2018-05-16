package com.cclean.terminal.model2;

import java.io.Serializable;
import java.util.List;

/**
 * @author yulq
 * @create 2018-04-20 13:10
 * @desc 角色集合
 **/
public class AuthorityDetailList implements Serializable {
    private static final long serialVersionUID = 1117777839007449339L;
    //权限类别:1工厂权限(工厂ID)  2酒店权限(酒店ID) 3物流权限(线路ID)
    private int type;
    //名称 工厂权限 酒店权限 物流权限
    private String title;
    //对应的ID集合
    private List<String> content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
