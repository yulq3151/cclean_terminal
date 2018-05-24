package com.cclean.terminal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.config.Result;
import com.cclean.terminal.constant.Constant;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.Property;
import com.cclean.terminal.model2.VersionInfo;
import com.cclean.terminal.service.PropertyService;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.util.TestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hubin on 2018/3/23.
 * 产权所有者相关逻辑
 */
@Service
public class PropertyServiceImpl implements PropertyService{

    private static Logger logger = LoggerFactory.getLogger(PropertyServiceImpl.class);


    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Override
    public Result propertys(String accessToken){
        Result result = Result.success();
        try{
            if (accessToken == null) return Result.paramNull();
            logger.info("accessToken value is:{}" , accessToken);
            // 产权所有者列表
            List<Property> propertyList = new ArrayList<>();
            String url = cloudUrl+"/cloud/basic/property/list";
            logger.info("propertyList cloudUrl is:{}",url);
            JSONObject jsonParam = new JSONObject();
            String httpEntitys = HttpUtil.doPost(url,accessToken,jsonParam);
            logger.info("产权：respose:{}",httpEntitys);
            JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                logger.error("{}{}{}",url,retCode,retInfo);
                result.setCodeInfo(retCode,retInfo );
                return result;
            }

            JSONArray jsonArray = jsonObj.getJSONArray("data");
            if (jsonArray == null) return Result.objNull();

            if(jsonArray!=null&&jsonArray.size()>0) {
                for (int i = 0, j = jsonArray.size(); i < j; i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    if (json == null) return Result.objNull();
                    Property property = new Property();
                    property.setId(json.getString("id"));
                    property.setName(json.getString("name"));
                    propertyList.add(property);
                }
            }
            result.setData(propertyList);
            return result;
        }catch (Exception e){
            logger.error(Constant.RET_CODE_DEBUG, e);
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getMessage());
            return result;
        }
    }


    /**
     *  版本更新
     * @param param
     * @return
     */
    @Override
    public VersionInfo versionUpdate(String param, String token) throws BusinessException {
        JSONObject jsonObj = JSONObject.parseObject(param);
        jsonObj.put("versionType","4"); // 1、酒店APP 2、物流APP 3.配货APP 4.终端
        String url = cloudUrl+"/cloud/basic/version/latest";
        String httpEntitys = HttpUtil.doPost(url, "", jsonObj);
        logger.info("版本更新：respose:{}",httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }

        VersionInfo versionInfo = JSONObject.parseObject(jsonObject1.getString("data"), VersionInfo.class);
        return versionInfo;
    }
}
