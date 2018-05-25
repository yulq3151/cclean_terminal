package com.cclean.terminal.mobileService;

import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model2.Cagecar;
import com.cclean.terminal.model2.CagecarUseLog;
import com.cclean.terminal.model2.LinenPackM;

import java.util.List;

/**
 * @author yulq
 * @create 2018-05-23 11:37
 * @desc
 **/
public interface CagecarService {
    boolean register(String token, List<String> codes, String factoryId) throws BusinessException;

    List<Cagecar> list(String token, List<String> codes) throws BusinessException;

    boolean cagecarUse(String token, int status, String userId, String factoryId, List<String> codes, List<String> packCodes) throws BusinessException;

    CagecarUseLog recored(String token, String code) throws BusinessException;

    List<LinenPackM> findPacks(String token, String code) throws BusinessException;

    PageMo<CagecarUseLog> query(String token, int pageNum, int pageSize, List<Integer> statusArr, String cagecarCode, String userTimeStart, String useTimeEnd, String type) throws BusinessException;
}
