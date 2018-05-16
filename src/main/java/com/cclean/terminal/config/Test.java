package com.cclean.terminal.config;

import com.cclean.terminal.constant.Constant;
import com.cclean.terminal.model.Sku;
import com.cclean.terminal.util.FastJsonUtil;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.util.TestUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

/**
 * 测试
 */
public class Test {

    public static void main(String[] args) {
/*
        Sku sku = TestUtil.getSku();
        String json = FastJsonUtil.toJson(sku);
        System.out.println(json);

        Sku sku1 = FastJsonUtil.toObject(json, Sku.class);
        System.out.println(sku1.toString());

        List<Sku> skus = TestUtil.getSkuList(5);

        String jsonA = FastJsonUtil.toJson(skus);
        System.out.println(jsonA);

        List<Sku> skus1 = FastJsonUtil.toList(jsonA, Sku.class);
        System.out.println(skus1.toString());


*/

        String authCode = "YJ-001";
        // 校验码
        String authKey = DigestUtils.md5Hex(Constant.CHAOJIE + authCode + Constant.ADX).toLowerCase();

        System.out.println(authKey);
    }
}
