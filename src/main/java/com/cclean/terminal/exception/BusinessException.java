package com.cclean.terminal.exception;

public class BusinessException extends Exception {

    private String errorCode;

    public BusinessException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
    public BusinessException(){

    }

    public Throwable fillInStackTrace() {
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
