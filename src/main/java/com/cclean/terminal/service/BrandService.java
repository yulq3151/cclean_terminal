package com.cclean.terminal.service;

import com.cclean.terminal.config.Result;
import com.cclean.terminal.exception.BusinessException;

/**
 * Created by hubin on 2018/3/23.
 */
public interface BrandService {

    /**
     * 品牌列表
     * @return
     * @throws BusinessException
     */
    Result brands(String accessToken);
}
