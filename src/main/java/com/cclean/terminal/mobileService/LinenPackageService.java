package com.cclean.terminal.mobileService;

import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.model2.LinenPackage;
import com.cclean.terminal.model2.LinenPackageRecord;
import com.cclean.terminal.model2.LinenPackageStacount;
import com.cclean.terminal.model2.LinenPackageStatistics;
import com.cclean.terminal.vo.LinenPackageVO;

import java.util.List;

/**
 * @author yulq
 * @create 2018-05-11 9:33
 * @desc
 **/
public interface LinenPackageService {

    List<String> register(String token, List<String> codes, int type, String color, String batch);

    boolean update(String token, LinenPackageVO packge);

    boolean circulate(String token, List<String> codes, int linentype, int usetype, String userId, String hotelId, String pointId);

    PageMo<LinenPackage> pagePackage(String token, LinenPackageVO linenPackageVO);

    List<LinenPackage> listPackage(String token, List<String> codes);

    PageMo<LinenPackageRecord> record(String token, String code, int pageNum, int pageSize);

    List<LinenPackageStatistics> fineReport(String token, String userId, int beginNum, int endNum);

    LinenPackageStacount dirtyReport(String token, String userId, int beginNum, int endNum);
}
