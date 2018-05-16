package com.cclean.terminal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.config.Result;
import com.cclean.terminal.constant.Constant;
import com.cclean.terminal.entity.Page;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.Factory;
import com.cclean.terminal.service.FactoryService;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.util.PowerUtils;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.util.TestUtil;
import com.cclean.terminal.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 * 供应商相关逻辑
 */
@Service
public class FactoryServiceImpl implements FactoryService {

    private static Logger logger = LoggerFactory.getLogger(FactoryServiceImpl.class);

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Autowired
    PowerUtils powerUtils;

    @Override
    public Result factorys(String accessToken, PageVO pageVO) {
        Result result = Result.success();
        if (pageVO.getPageNum() == null && pageVO.getPageSize() == null) {
            result.setCodeInfo("00100", "分页参数必填");
        }

        if (accessToken == null) return Result.paramNull();
        logger.info("accessToken value is:{}", accessToken);

        List<String> factoryIds = powerUtils.getFactoryIds(accessToken);
        if (factoryIds == null || factoryIds.size() <= 0) {
            result.setCodeInfo(Constant.RET_CODE_ERROR, "权限不足");
            return result;
        }

        // 工厂列表
        List<Factory> factoryList = new ArrayList<>();
        String url = linenUrl + "/cloud/manage/v1/factory/page";
        logger.info("factorys cloudUrl is:{}", url);
        String js = JSONArray.toJSONString(pageVO);
        JSONObject jsonParam = JSONArray.parseObject(js);
        jsonParam.put("factoryIds", factoryIds);
        String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
        logger.info("工厂列表:{}", httpEntitys);
        JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObj.getString("retCode");
        if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
            String retInfo = jsonObj.getString("retInfo");
            result.setCodeInfo(retCode, retInfo);
            return result;
        }

        JSONObject dataJson = jsonObj.getJSONObject("page");
        if (dataJson == null) return Result.objNull();
        JSONArray jsonArray = dataJson.getJSONArray("list");
        if (jsonArray == null) return Result.objNull();

        for (int i = 0, j = jsonArray.size(); i < j; i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            //if(json.getString("id").equals(factoryIds.get(0))){
            Factory factory = new Factory();
            factory.setId(json.getString("id"));
            factory.setName(json.getString("name"));
            factory.setAddress(json.getString("address"));
            factoryList.add(factory);
            //}
        }

        PageMo<Factory> page = new PageMo<Factory>(factoryList, pageVO,
                dataJson.getInteger("total"));
        result.setData(page);
        return result;
    }

    /**
     * 根据工厂id获取工厂信息
     *
     * @param token
     * @param fids
     * @return
     * @throws BusinessException
     */
    @Override
    public List<Factory> findFactorysByids(String token, List<String> fids) throws BusinessException {
        List<Factory> list = new ArrayList<>();
        if (fids == null || fids.size() == 0) {
            return list;
        }
        String url = cloudUrl + "/cloud/basic/factory/list";
        JSONObject param = new JSONObject();
        param.put("factoryIds", fids);
        String httpEntitys = HttpUtil.doPost(url, token, param);
        logger.info("工厂列表:{}", httpEntitys);
        JSONObject object = JSONObject.parseObject(httpEntitys);
        String retCode = object.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, object.getString("retInfo"));
        }
        list = JSONObject.parseArray(object.getString("data"), Factory.class);
        return list;

    }

}