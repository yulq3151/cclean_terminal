package com.cclean.terminal.service;

import com.cclean.terminal.config.Result;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.LinenPack;
import com.cclean.terminal.model.SkuInfoEntity;
import com.cclean.terminal.vo.LinenDirtVO;
import com.cclean.terminal.vo.LinenScrapVO;
import com.cclean.terminal.vo.LinenUpdateVO;
import com.cclean.terminal.vo.RfidsVO;

import java.util.List;
import java.util.Set;

/**
 * Created by hubin on 2018/3/23.
 */
public interface LinenService {

    Result linens(String accessToken, RfidsVO rfidsVO) throws BusinessException;

    Result update(String accessToken, LinenUpdateVO linenUpdateVO);

    Result scrap(String accessToken, LinenScrapVO linenScrapVO);

    Result dirt(String accessToken, LinenDirtVO linenDirtVO) throws BusinessException;

    Result recheck(String token, Set<String> ids, Set<String> rfids) throws BusinessException;

    Result pack(String accessToken, RfidsVO rfidsVO) throws BusinessException;

    Result types(String accessToken) throws BusinessException;

    LinenPack findPacksByRfids(String token, List<String> rfids) throws BusinessException;

    List<SkuInfoEntity> findSkuByRfid(String token, List<String> rfids) throws BusinessException;
}
