package com.cclean.terminal.mobileService;

import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model2.*;
import com.cclean.terminal.vo.LinenPackageVO;

import java.util.List;

/**
 * @author yulq
 * @create 2018-05-11 9:33
 * @desc
 **/
public interface LinenPackageService {

    List<String> register(String token, List<String> codes, int type, String color, String batch) throws BusinessException;

    boolean update(String token, LinenPackageVO packge) throws BusinessException;

    boolean circulate(String token, List<String> codes, int linentype, int usetype, String userId, String hotelId, String pointId) throws BusinessException;

    PageMo<LinenPackage> pagePackage(String token, LinenPackageVO linenPackageVO) throws BusinessException;

    List<LinenPackage> listPackage(String token, List<String> codes) throws BusinessException;

    PageMo<LinenPackageRecord> record(String token, String code, int pageNum, int pageSize) throws BusinessException;

    List<LinenPackageStatistics> fineReport(String token, String userId, int beginNum, int endNum) throws BusinessException;

    LinenPackageStacount dirtyReport(String token, String userId, int beginNum, int endNum) throws BusinessException;

    LinenPackageKZ packageKZ(String token, List<String> codes) throws BusinessException;

    LinenPackageKZ findPackageKZ(String token, String packageKZ) throws BusinessException;

    boolean borrow(String token, String userId, List<String> codes) throws BusinessException;
}
