package com.cclean.terminal.model2;


import java.io.Serializable;

/**
 * 关于我们
 */
public class AboutUsModel implements Serializable{

    private String logoUrl;//图片地址
    private String appVersion;//当前版本
    private String companyBrief;//公司简介
    private String copyrightMessage;//版权信息
    private String website;//网址
    private String telephone;//电话
    private String email;//邮箱
    private String copyright;//版权

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getCompanyBrief() {
        return companyBrief;
    }

    public void setCompanyBrief(String companyBrief) {
        this.companyBrief = companyBrief;
    }

    public String getCopyrightMessage() {
        return copyrightMessage;
    }

    public void setCopyrightMessage(String copyrightMessage) {
        this.copyrightMessage = copyrightMessage;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    @Override
    public String toString() {
        return "AboutUsModel{" +
                "logoUrl='" + logoUrl + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", companyBrief='" + companyBrief + '\'' +
                ", copyrightMessage='" + copyrightMessage + '\'' +
                ", website='" + website + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", copyright='" + copyright + '\'' +
                '}';
    }
}
