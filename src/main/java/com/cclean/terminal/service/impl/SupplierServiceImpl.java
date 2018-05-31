package com.cclean.terminal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.config.Result;
import com.cclean.terminal.constant.Constant;
import com.cclean.terminal.model.Supplier;
import com.cclean.terminal.service.SupplierService;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.vo.PageVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 * 供应商相关逻辑
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    private static final  Integer pageSize = 100;

    private static final  Integer pageNum = 1;

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Override
    public Result suppliers(String accessToken){
        Result result = Result.success();
        try{
            if (accessToken == null) return Result.paramNull();

            PageVO pageVO = new PageVO();
            pageVO.setPageNum(pageNum);
            pageVO.setPageSize(pageSize);
            // 供应商列表
            List<Supplier> supplierList =new ArrayList<>();
            String url = linenUrl+"/cloud/manage/v1/supplier/page";
            String js = JSONArray.toJSONString(pageVO);
            JSONObject jsonParam = JSONArray.parseObject(js);
            String httpEntitys = HttpUtil.doPost(url,accessToken,jsonParam);
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
                Supplier supplier = new Supplier();
                supplier.setId(json.getString("id"));
                supplier.setName(json.getString("name"));
                supplierList.add(supplier);
            }
            result.setData(supplierList);
            return result;
        }catch (Exception e){
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getMessage());
            return result;
        }
    }
}
