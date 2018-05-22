package com.cclean.terminal.config;


import com.cclean.terminal.constant.Constant;

/**
 * 返回结果
 */
public class Result {

    // 返回码
    private String retCode;
    // 返回信息
    private String retInfo;
    // 返回数据
    private Object data;

    public static Result success(){
        return new Result(Constant.RET_CODE_SUCCESS, Constant.RET_INFO_SUCCESS);
    }

    public static Result success(Object data){
        return new Result(Constant.RET_CODE_SUCCESS, Constant.RET_INFO_SUCCESS, data);
    }

    public static Result error(){
        return new Result(Constant.RET_CODE_ERROR, Constant.RET_INFO_ERROR);
    }

    public static Result error(String retInfo){
        return new Result(Constant.RET_CODE_ERROR, retInfo);
    }

    public static Result paramNull(){
        return new Result(Constant.RET_CODE_PARAM_NULL, Constant.RET_INFO_PARAM_NULL);
    }

    public static Result idNull(){
        return new Result(Constant.RET_CODE_ID_NULL, Constant.RET_INFO_ID_NULL);
    }

    public static Result objNull(){
        return new Result(Constant.RET_CODE_OBJ_NULL, Constant.RET_INFO_OBJ_NULL);
    }

    public Result(String retCode, String retInfo) {
        this.retCode = retCode;
        this.retInfo = retInfo;
    }

    private Result(String retCode, String retInfo, Object data) {
        this.retCode = retCode;
        this.retInfo = retInfo;
        this.data = data;
    }
    public Result(Object data){
        this.retCode = Constant.RET_CODE_SUCCESS;
        this.retInfo = Constant.RET_INFO_SUCCESS;
        this.data = data;
    }

    public void setCodeInfo(String retCode, String retInfo) {
        this.retCode = retCode;
        this.retInfo = retInfo;
    }

    public Result() {
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
