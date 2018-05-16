package com.cclean.terminal.mobileService.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.mobileService.LinenPackageService;
import com.cclean.terminal.model2.LinenPackage;
import com.cclean.terminal.vo.LinenPackageVO;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author yulq
 * @create 2018-05-11 9:33
 * @desc
 **/
public class LinenPackageServiceImpl implements LinenPackageService {

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    /**
     *  布草袋登记
     * @param token
     * @param code
     * @param type
     * @param color
     * @return
     */
    @Override
    public LinenPackage register(String token, String code, String type, String color) {

        return null;

    }

    /**
     *  布草袋分页查询
     * @param token
     * @param linenPackageVO
     * @return
     */
    @Override
    public PageMo<LinenPackage> pageBag(String token, LinenPackageVO linenPackageVO){
        JSONObject param = JSON.parseObject(JSONObject.toJSONString(linenPackageVO));
        String url = cloudUrl+"";
        return null;
    }


}
