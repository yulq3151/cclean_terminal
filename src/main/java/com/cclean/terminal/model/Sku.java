package com.cclean.terminal.model;

/**
 * Sku
 */
public class Sku {

    // SKU的ID
    private String id;
    // 编号
    private String code;
    // 简码
    private String briefCode;
    // 名称
    private String name;
    // 尺寸ID
    private String size;
    //
    private String sizeValue;
    //类型Id
    private  String type;
    //捆扎数
    private Integer packCnt;

    public String getSizeValue() {
        return sizeValue;
    }

    public void setSizeValue(String sizeValue) {
        this.sizeValue = sizeValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBriefCode() {
        return briefCode;
    }

    public void setBriefCode(String briefCode) {
        this.briefCode = briefCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPackCnt() {
        return packCnt;
    }

    public void setPackCnt(Integer packCnt) {
        this.packCnt = packCnt;
    }

    @Override
    public String toString() {
        return "Sku{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", briefCode='" + briefCode + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", type='" + type + '\'' +
                ", packCnt='" + packCnt + '\'' +
                '}';
    }
}
