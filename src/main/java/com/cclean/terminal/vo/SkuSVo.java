package com.cclean.terminal.vo;

/**
 * @author yulq
 * @create 2018-04-13 15:04
 * @desc 生成配送单时的sku扎数记录
 **/
public class SkuSVo {
    //sku
    private String skuId;
    //应配扎数
    private Integer zpick;
    //实配扎数
    private Integer scanZpick;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getZpick() {
        return zpick;
    }

    public void setZpick(Integer zpick) {
        this.zpick = zpick;
    }

    public Integer getScanZpick() {
        return scanZpick;
    }

    public void setScanZpick(Integer scanZpick) {
        this.scanZpick = scanZpick;
    }

    @Override
    public String toString() {
        return "SkuSVo{" +
                "skuId='" + skuId + '\'' +
                ", zpick=" + zpick +
                ", scanZpick=" + scanZpick +
                '}';
    }
}
