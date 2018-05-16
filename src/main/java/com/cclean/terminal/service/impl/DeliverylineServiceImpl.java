package com.cclean.terminal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.Deliveryline;
import com.cclean.terminal.service.DeliverylineService;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author yulq
 * @create 2018-04-11 15:57
 * @desc 线路
 **/
@Service
public class DeliverylineServiceImpl implements DeliverylineService {
    private static Logger logger = LoggerFactory.getLogger(DeliverylineServiceImpl.class);

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Value("${invoke.deliveryline.page}")
    private String deliverylines;

    @Override
    public Deliveryline add(String name) {
        return null;
    }

    @Override
    public Deliveryline update(String id) {
        return null;
    }

    @Override
    public Deliveryline delete(String id) {
        return null;
    }

    /**
     * 线路分页列表
     *
     * @param token
     * @param pageVO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageMo getdeliverylines(String token, PageVO pageVO) throws BusinessException {
        String url = linenUrl + deliverylines;
        JSONObject param = new JSONObject();
        if (pageVO.getPageNum() != null) {
            param.put("pageNum", pageVO.getPageNum());
        }
        if (pageVO.getPageSize() != null) {
            param.put("pageSize", pageVO.getPageSize());
        }
        param.put("state", 1);
        String postjson = HttpUtil.doPost(url, token, param);
        logger.info("线路列表：{}",postjson);
        JSONObject jsonObject = JSONObject.parseObject(postjson);
        String retCode = jsonObject.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject.getString("retInfo"));
        }
        int total = jsonObject.getIntValue("total");
        JSONArray jsonArray = jsonObject.getJSONArray("deliverylines");
        PageMo pageMo = new PageMo(jsonArray, pageVO, total);
        return pageMo;
    }
}
