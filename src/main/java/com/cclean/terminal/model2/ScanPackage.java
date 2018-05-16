package com.cclean.terminal.model2;

import java.io.Serializable;

/**
 * @author yulq
 * @create 2018-04-17 10:57
 * @desc
 **/
public class ScanPackage implements Serializable {


    private static final long serialVersionUID = 3102462831458032952L;

    private String id;
    private String scanId;
    private String pageNo;
    private String packageRfid;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPackageRfid() {
        return packageRfid;
    }

    public void setPackageRfid(String packageRfid) {
        this.packageRfid = packageRfid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ScanPackage{" +
                "id='" + id + '\'' +
                ", scanId='" + scanId + '\'' +
                ", pageNo='" + pageNo + '\'' +
                ", packageRfid='" + packageRfid + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
