package com.cclean.terminal.mobileController;

import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.mobileService.LinenPackageService;
import com.cclean.terminal.model.Result;
import com.cclean.terminal.model2.LinenPackage;
import com.cclean.terminal.model2.LinenPackageRecord;
import com.cclean.terminal.model2.LinenPackageStacount;
import com.cclean.terminal.model2.LinenPackageStatistics;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.vo.LinenPackageVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yulq
 * @create 2018-05-14 9:32
 * @desc
 **/
@RestController
@RequestMapping("/package")
public class linenPackageController extends BaseMController {

    @Resource
    private LinenPackageService packageService;

    /**
     * 布草袋登记
     *
     * @param request
     * @param param
     * @return
     * @throws BusinessException
     */
    @PostMapping("/register")
    public Result register(HttpServletRequest request, @RequestBody String param) throws BusinessException {
        String token = getToken(request);
        JSONObject obj = JSONObject.parseObject(param);
        if (obj == null) {
            throw new BusinessException("00001", "参数不足");
        }
        List<String> codes = JSONObject.parseArray(obj.getString("codes"), String.class);
        int type = obj.getIntValue("type");
        String color = obj.getString("color");
        String batch = obj.getString("batch");
        if (type == 0) {
            throw new BusinessException("00001", "类型未知");
        }
        if (codes == null || codes.size() == 0) {
            throw new BusinessException("00001", "参数不足");
        }
        if (StringUtils.isHasEmpty(color, batch)) {
            throw new BusinessException("00001", "参数不足");
        }
        List<String> list = this.packageService.register(token, codes, type, color, batch);
        return new Result(list);
    }

    /**
     * 修改布草袋
     *
     * @param request
     * @param packge
     * @return
     * @throws BusinessException
     */
    @PostMapping("/update")
    public Result update(HttpServletRequest request, @RequestBody LinenPackageVO packge) throws BusinessException {
        String token = getToken(request);
        boolean bl = this.packageService.update(token, packge);
        return new Result(bl);
    }

    /**
     * 布草袋的领用/归还
     *
     * @param request
     * @param param
     * @return
     * @throws BusinessException
     */
    @PostMapping("/circulate")
    public Result circulate(HttpServletRequest request, @RequestBody String param) throws BusinessException {
        String token = getToken(request);
        JSONObject object = JSONObject.parseObject(param);
        List<String> codes = JSONObject.parseArray(object.getString("codes"), String.class);
        if (codes == null || codes.size() == 0) {
            throw new BusinessException("00001", "请传入布草袋编码");
        }
        int linentype = object.getIntValue("linentype");
        int usetype = object.getIntValue("usetype");
        String userId = object.getString("userId");
        if (linentype != 1 && linentype != 2) {
            throw new BusinessException("00001", "布草袋类型未知");
        }
        if (usetype != 1 && usetype != 2) {
            throw new BusinessException("00001", "布草袋状态未知");
        }
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("00001", "使用者未知");
        }
        String hotelId = object.getString("hotelId");
        String pointId = object.getString("pointId");
        if (linentype == 1 && usetype == 2) {
            if (StringUtils.isHasEmpty(hotelId, pointId)) {
                throw new BusinessException("00001", "净布草出库需有酒店配送点");
            }
        }
        boolean bl = this.packageService.circulate(token, codes, linentype, usetype, userId, hotelId, pointId);
        return new Result(bl);
    }

    /**
     * 布草袋分页查询
     *
     * @param request
     * @param packageVO
     * @return
     */
    @PostMapping("/page")
    public Result pagePackage(HttpServletRequest request, @RequestBody LinenPackageVO packageVO) throws BusinessException {
        String token = getToken(request);
        Integer num = packageVO.getPageNum();
        Integer size = packageVO.getPageSize();
        if (num == null || size == null) {
            throw new BusinessException("00001", "分页参数不足");
        }
        if (num <= 0 || size <= 0) {
            throw new BusinessException("00001", "分页参数错误");
        }
        PageMo<LinenPackage> pageMo = this.packageService.pagePackage(token, packageVO);
        return new Result(pageMo);
    }

    /**
     * 根据编码批量查询布草袋
     *
     * @param request
     * @param
     * @return
     */
    @PostMapping("/list")
    public Result listPackage(HttpServletRequest request, @RequestBody String param) throws BusinessException {
        String token = getToken(request);
        if (!param.contains("codes")) {
            throw new BusinessException("00001", "参数不足");
        }
        JSONObject object = JSONObject.parseObject(param);
        List<String> codes = JSONObject.parseArray(object.getString("codes"), String.class);
        List<LinenPackage> list = this.packageService.listPackage(token, codes);
        return new Result(list);
    }

    /**
     * 查询布草袋的使用记录
     *
     * @param request
     * @return
     */
    @PostMapping("/record")
    public Result record(HttpServletRequest request, @RequestBody String param) throws BusinessException {
        String token = getToken(request);
        JSONObject object = JSONObject.parseObject(param);
        String code = object.getString("code");
        if (code == null) {
            throw new BusinessException("00001", "请传入布草袋code");
        }
        int pageNum = object.getIntValue("pageNum");
        int pageSize = object.getIntValue("pageSize");
        if (pageNum <= 0 || pageSize <= 0) {
            throw new BusinessException("00001", "分页参数错误");
        }
        PageMo<LinenPackageRecord> record = this.packageService.record(token, code, pageNum, pageSize);
        return new Result(record);
    }

    /**
     * 净布草报表
     *
     * @param request
     * @return
     */
    @PostMapping("/fine/reprot")
    public Result fineReprot(HttpServletRequest request, @RequestBody String param) throws BusinessException {
        String token = getToken(request);
        JSONObject object = JSONObject.parseObject(param);
        String userId = object.getString("userId");
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("00001", "用户不能为空");
        }
        int beginNum = object.getIntValue("beginNum");
        int endNum = object.getIntValue("endNum");
        if (beginNum <= 0 || endNum <= 0) {
            throw new BusinessException("00001", "请填写正确的天数");
        }
        List<LinenPackageStatistics> list = this.packageService.fineReport(token, userId, beginNum, endNum);
        return new Result(list);
    }

    /**
     * 脏布草报表
     *
     * @param request
     * @return
     */
    @PostMapping("/dirty/reprot")
    public Result dirtyReprot(HttpServletRequest request, @RequestBody String param) throws BusinessException {
        String token = getToken(request);
        JSONObject object = JSONObject.parseObject(param);
        String userId = object.getString("userId");
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("00001", "用户不能为空");
        }
        int beginNum = object.getIntValue("beginNum");
        int endNum = object.getIntValue("endNum");
        if (beginNum <= 0 || endNum <= 0) {
            throw new BusinessException("00001", "请填写正确的天数");
        }
        LinenPackageStacount stacount = this.packageService.dirtyReport(token, userId, beginNum, endNum);
        return new Result(stacount);
    }

}
