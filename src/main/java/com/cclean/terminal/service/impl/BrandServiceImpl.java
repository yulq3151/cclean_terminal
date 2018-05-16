package com.cclean.terminal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.config.Result;
import com.cclean.terminal.constant.Constant;
import com.cclean.terminal.model.Brand;
import com.cclean.terminal.service.BrandService;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.util.Utils;
import com.cclean.terminal.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 * 品牌相关逻辑
 */
@Service
public class BrandServiceImpl implements BrandService{

    private static Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Override
    public Result brands(String accessToken){
        Result result = Result.success();
        try {
            if (accessToken == null) return Result.paramNull();
            logger.info("accessToken value is:{}", accessToken);
            PageVO pageVO = new PageVO();
            pageVO.setPageNum(Utils.pageNum);
            pageVO.setPageSize(Utils.pageSize);
            // 获取所有的品牌列表
            List<Brand> brandList = new ArrayList<>();
            String url = linenUrl + "/cloud/manage/v1/brand/page";
            logger.info("brands cloudUrl is:{}" , url);
            String js = JSONArray.toJSONString(pageVO);
            JSONObject jsonParam = JSONArray.parseObject(js);
            String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
            logger.info("品牌列表 Responses content: {}", httpEntitys);

            JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                result.setCodeInfo(retCode,retInfo );
                return result;
            }

            JSONObject dataJson = jsonObj.getJSONObject("page");
            if (dataJson == null) return Result.objNull();
            JSONArray jsonArray = dataJson.getJSONArray("list");
            if (jsonArray == null) return Result.objNull();

            for (int i = 0, j = jsonArray.size(); i < j; i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                if (json == null) return Result.objNull();
                Brand brand = new Brand();
                brand.setId(json.getString("id"));
                brand.setCode(json.getString("code"));
                brand.setName(json.getString("name"));
                brandList.add(brand);
            }
            result.setData(brandList);
            return result;
        }catch (Exception e){
            logger.error(Constant.RET_CODE_DEBUG, e);
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getMessage());
            return result;
        }
    }
}
