package com.cclean.terminal.service;

import com.alibaba.fastjson.JSONArray;
import com.cclean.terminal.config.Result;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.Sku;
import com.cclean.terminal.model.SkuReceived;
import com.cclean.terminal.vo.IdVO;
import com.cclean.terminal.vo.RfidsVO;
import com.cclean.terminal.vo.SkuVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SkuService {

    Result statistics(String accessToken, RfidsVO rfidsVO) throws BusinessException;


    Result page(String accessToken, SkuVO skuVO);

    Sku findSkuById(String accessToken, IdVO idVO) throws BusinessException;

    Map<String, Object> stringToBean(JSONArray skuListJson, String accessToken) throws BusinessException;

    Map<String, Sku> findSkusByIds(Set<String> ids) throws BusinessException;

    List<Map<String, String>> findSkuByRfid(String token, List<String> rfids, String scanTime) throws BusinessException;
}
