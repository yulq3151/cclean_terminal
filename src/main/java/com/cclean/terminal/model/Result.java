package com.cclean.terminal.model;


import com.cclean.terminal.constant.Constant;

/**
 * 返回结果
 */
public class Result<T> {

    private String retCode;
    private String retInfo;
    private T data;

    public Result() {
        this.retCode = Constant.RET_CODE_SUCCESS;
        this.retInfo = Constant.RET_INFO_SUCCESS;
    }
    public Result(T data) {
        this.retCode = Constant.RET_CODE_SUCCESS;
        this.retInfo = Constant.RET_INFO_SUCCESS;
        this.data = data;
    }

    public Result(String retCode,String retInfo) {
        this.retCode = retCode;
        this.retInfo = retInfo;
        this.data = null;
    }
    public Result(String retInfo) {
        this.retCode = Constant.RET_CODE_DEBUG;
        this.retInfo = retInfo;
        this.data = null;
    }
    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(String retInfo) {
        this.retInfo = retInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
