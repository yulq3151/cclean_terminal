package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 *  版本更新信息
 */
public class VersionInfo implements Serializable {
    private static final long serialVersionUID = -356997378819942511L;
    //版本ID
    private String versionid;
    //版本号
    private String versioncode;
    //版本名称
    private String versionname;
    //下载地址
    private String url;
    //标题
    private String title;
    //内容
    private String content;
    //下载次数
    private Integer updatecount;
    //版本信息1、酒店APP 2、物流APP 3.配货APP
    private String versiontype;
    //强制更新：0为否，1为是
    private Integer forceUpdate;

    public String getVersionid() {
        return versionid;
    }

    public void setVersionid(String versionid) {
        this.versionid = versionid;
    }

    public String getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(String versioncode) {
        this.versioncode = versioncode == null ? null : versioncode.trim();
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname == null ? null : versionname.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getUpdatecount() {
        return updatecount;
    }

    public void setUpdatecount(Integer updatecount) {
        this.updatecount = updatecount;
    }

    public String getVersiontype() {
        return versiontype;
    }

    public void setVersiontype(String versiontype) {
        this.versiontype = versiontype == null ? null : versiontype.trim();
    }

    public Integer getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Integer forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    @Override
    public String toString() {
        return "VersionInfo{" +
                "versionid=" + versionid +
                ", versioncode='" + versioncode + '\'' +
                ", versionname='" + versionname + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", updatecount=" + updatecount +
                ", versiontype='" + versiontype + '\'' +
                ", forceUpdate=" + forceUpdate +
                '}';
    }
}