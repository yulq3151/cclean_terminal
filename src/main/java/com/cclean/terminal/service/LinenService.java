package com.cclean.terminal.service;

import com.cclean.terminal.config.Result;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.*;
import com.cclean.terminal.model2.LinenPackM;
import com.cclean.terminal.vo.*;

import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 */
public interface LinenService {

    Result linens(String accessToken, RfidsVO rfidsVO) throws BusinessException;

    Result update(String accessToken,LinenUpdateVO linenUpdateVO);

    Result scrap(String accessToken,LinenScrapVO linenScrapVO);

    Result dirt(String accessToken,LinenDirtVO linenDirtVO) throws BusinessException;

    Result recheck(String accessToken,LinenRecheckVO linenRecheckVO);

    Result pack(String accessToken,RfidsVO rfidsVO);

    Result types(String accessToken);

    LinenPack findPacksByRfids(String token, List<String> rfids) throws BusinessException;

    List<SkuInfoEntity> findSkuByRfid(String token, List<String> rfids) throws BusinessException;
}
