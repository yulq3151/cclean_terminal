package com.cclean.terminal.vo;

/**
 * 只有id参数时公用
 */
public class IdVO{

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IdVO(){

    }

    public IdVO(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdVO{" +
                "id='" + id + '\'' +
                '}';
    }
}
