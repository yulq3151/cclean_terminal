package com.cclean.terminal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.config.Result;
import com.cclean.terminal.constant.Constant;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.model.ScrapReason;
import com.cclean.terminal.service.ScrapService;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.vo.PageVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 * 报废信息相关逻辑
 */
@Service
public class ScrapServiceImpl implements ScrapService {

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Override
    public Result scrapreason(String accessToken, PageVO pageVO)  {
        Result result = Result.success();
        try{
            if (pageVO.getPageNum() == null && pageVO.getPageSize() == null) {
                result.setCodeInfo("00100", "分页参数必填");
            }

            if (accessToken == null) return Result.paramNull();

            // 报废原因列表
            List<ScrapReason> scrapReasonList =new ArrayList<>();
            String url = linenUrl+"/cloud/manage/v1/dictionary/page";
            String js = JSONArray.toJSONString(pageVO);
            JSONObject jsonParam = JSONArray.parseObject(js);
            jsonParam.put("type","scrapReason");
            String httpEntitys = HttpUtil.doPost(url,accessToken,jsonParam);
            JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                result.setCodeInfo(retCode,retInfo );
                return result;
            }

            JSONObject jsonList = jsonObj.getJSONObject("list");
            if (jsonList == null) return Result.objNull();
            JSONArray jsonArray = jsonList.getJSONArray("list");
            if (jsonArray == null) return Result.objNull();

            for (int i = 0, j = jsonArray.size(); i < j; i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                if (json == null) return Result.objNull();
                ScrapReason scrapReason = new ScrapReason();
                scrapReason.setId(json.getString("id"));
                scrapReason.setScrapReason(json.getString("value"));
                scrapReasonList.add(scrapReason);
            }

            PageMo<ScrapReason> page = new PageMo<>(scrapReasonList,pageVO,
                    jsonList.getInteger("total"));
            result.setData(page);
            return result;
        }catch (Exception e){
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getMessage());
            return result;
        }
    }
}
