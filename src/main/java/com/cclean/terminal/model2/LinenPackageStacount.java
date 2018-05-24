package com.cclean.terminal.model2;

/**
 * @author yulq
 * @create 2018-05-16 14:54
 * @desc
 **/
public class LinenPackageStacount {
    //配送点ID
    private String pointId;
    //配送点名称
    private String pointName;
    //小于值 合计
    private Integer left;
    //区间值 合计
    private Integer middle;
    //大于值 合计
    private Integer right;

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getMiddle() {
        return middle;
    }

    public void setMiddle(Integer middle) {
        this.middle = middle;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }
}
