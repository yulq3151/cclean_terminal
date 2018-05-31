package com.cclean.terminal.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/22.
 */
@Component
public class PowerUtils {

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    public List<String> getFactoryIds(String token) throws BusinessException {
        List<String> facoryIds = new ArrayList<>();
        String url = cloudUrl + "/cloud/user/center/user/authority";
        JSONObject jsonParam = new JSONObject();
        JSONObject userDateJson = InvokeUtil.invokeResult(url, token, jsonParam);
        if (userDateJson == null) return facoryIds;
        JSONObject userJson = userDateJson.getJSONObject("authorityInfo");
        if (userJson == null) return facoryIds;
        JSONObject cloudJson = userJson.getJSONObject("terminalDev");
        JSONArray jsonArray = cloudJson.getJSONArray("authorityDetailList");
        if (jsonArray == null && jsonArray.size() <= 0) return facoryIds;

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject == null) return facoryIds;
            if (jsonObject.getString("type").equals("1")) {
                JSONArray ids = jsonObject.getJSONArray("content");
                if (ids == null && ids.size() <= 0) return facoryIds;
                for (i = 0; i < ids.size(); i++) {
                    facoryIds.add(ids.getString(i));
                }
            }
        }
        return facoryIds;
    }

}
