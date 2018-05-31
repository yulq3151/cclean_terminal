package com.cclean.terminal.service;

import com.cclean.terminal.config.Result;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.Factory;
import com.cclean.terminal.vo.PageVO;

import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 */
public interface FactoryService {

    Result factorys(String accessToken, PageVO pageVO) throws BusinessException;

    List<Factory> findFactorysByids(String token, List<String> fids) throws BusinessException;
}
