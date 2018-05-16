package com.cclean.terminal.vo;

/**
 * sku查询参数
 */
public class SkuVO extends PageVO{

    // 短码
    private String code;
    // 品牌
    private String brandId;
    // 类型
    private String codeType;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    @Override
    public String toString() {
        return "SkuVO{" +
                "pageNum=" + pageNum +
                ", code='" + code + '\'' +
                ", pageSize=" + pageSize +
                ", brand='" + brandId + '\'' +
                ", codeType='" + codeType + '\'' +
                '}';
    }
}
