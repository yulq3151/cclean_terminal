package com.cclean.terminal.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/22.
 */
@Component
public class PowerUtils {

    private static Logger logger = LoggerFactory.getLogger(PowerUtils.class);

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    public  List<String> getFactoryIds(String token){
        List<String> facoryIds = new ArrayList<>();
        try {
            String url = cloudUrl + "/cloud/user/center/user/authority";
            JSONObject jsonParam = new JSONObject();
            String  json = HttpUtil.doPostStr(url, token, jsonParam.toJSONString());
            JSONObject jsonObj = JSON.parseObject(json);
            String  retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                logger.error(retCode, "/cloud/user/center/user/authority:" + retInfo);
                return null;
            }

            JSONObject userDateJson = jsonObj.getJSONObject("data");
            if (userDateJson == null) return null;
            JSONObject userJson = userDateJson.getJSONObject("authorityInfo");
            if (userJson == null) return null;
            JSONObject cloudJson = userJson.getJSONObject("terminalDev");
            JSONArray jsonArray = cloudJson.getJSONArray("authorityDetailList");
            if (jsonArray == null && jsonArray.size() <= 0) return null;

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject == null) return null;
                if (jsonObject.getString("type").equals("1")) {
                    JSONArray ids = jsonObject.getJSONArray("content");
                    if (ids == null && ids.size() <= 0) return null;
                    for (i = 0; i < ids.size(); i++) {
                        facoryIds.add(ids.getString(i));
                    }
                }
            }
            return facoryIds;
        }catch (Exception e){
            logger.error(Constant.RET_CODE_DEBUG, e);
            return null;
        }
    }

    public  boolean isMenu(String token){
        try {
            String url = cloudUrl + "/cloud/user/center/user/authority";
            JSONObject jsonParam = new JSONObject();
            String  json = HttpUtil.doPostStr(url, token, jsonParam.toJSONString());
            JSONObject jsonObj = JSON.parseObject(json);
            String  retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                logger.error(retCode, "/cloud/user/center/user/authority:" + retInfo);
                return false;
            }

            JSONObject userDateJson = jsonObj.getJSONObject("data");
            if (userDateJson == null) return false;
            JSONObject userJson = userDateJson.getJSONObject("authorityInfo");
            if (userJson == null) return false;
            JSONObject cloudJson = userJson.getJSONObject("cloud");
            JSONObject menuJson = cloudJson.getJSONObject("roleMenu");
            if (menuJson == null ) return false;
            String menuStr = menuJson.toJSONString();
            if(menuStr.indexOf("功能权限")==-1){
                return false;
            }
            return true;
        }catch (Exception e){
            logger.error(Constant.RET_CODE_DEBUG, e);
            return false;
        }
    }
}
