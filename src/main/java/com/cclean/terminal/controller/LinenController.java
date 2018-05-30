package com.cclean.terminal.controller;

import com.cclean.terminal.config.Result;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.LinenPack;
import com.cclean.terminal.model.SkuInfoEntity;
import com.cclean.terminal.service.LinenService;
import com.cclean.terminal.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by hubin on 2018/3/23.
 * 布草相关
 */
@RestController
@RequestMapping("linen")
public class LinenController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LinenController.class);

    @Autowired
    LinenService linenService;

    /**
     * 添加/修改
     *
     * @param linenUpdateVO
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody(required = false) LinenUpdateVO linenUpdateVO, HttpServletRequest request) {
        if (linenUpdateVO == null) return Result.paramNull();
        return linenService.update(getToken(request), linenUpdateVO);
    }

    /**
     * 报废
     *
     * @param linenScrapVO
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/scrap", method = RequestMethod.POST)
    @ResponseBody
    public Map scrap(@RequestBody(required = false) LinenScrapVO linenScrapVO, HttpServletRequest request) {
        Result result = null;
        Map<String, Object> map = new HashMap<>();
        if (linenScrapVO == null) {
            result = Result.paramNull();
            map.put("retInfo", result.getRetInfo());
            map.put("retCode", result.getRetCode());
            map.put("linen", null);
            return map;
        }
        result = linenService.scrap(getToken(request), linenScrapVO);
        map.put("retInfo", result.getRetInfo());
        map.put("retCode", result.getRetCode());
        map.put("linen", result.getData());
        return map;
    }

    /**
     * 污物收货
     *
     * @param linenDirtVO
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/dirt", method = RequestMethod.POST)
    @ResponseBody
    public Result dirt(@RequestBody(required = false) LinenDirtVO linenDirtVO, HttpServletRequest request) throws BusinessException {
        if (linenDirtVO == null) return Result.paramNull();
        return linenService.dirt(getToken(request), linenDirtVO);
    }

    /**
     * 布草复核
     *
     * @param linenRecheckVO
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/recheck", method = RequestMethod.POST)
    @ResponseBody
    public Result recheck(@RequestBody LinenRecheckVO linenRecheckVO, HttpServletRequest request) throws BusinessException {
        String token = getToken(request);
        Set<String> ids = new HashSet<>(linenRecheckVO.getBasiss());
        if (ids == null || ids.size() == 0) {
            throw new BusinessException("00001", "请传入订单ID");
        }
        Set<String> rfids = new HashSet<>(linenRecheckVO.getRfids());
        if (rfids == null || rfids.size() == 0) {
            throw new BusinessException("00001", "请传入rfids");
        }
        return this.linenService.recheck(token, ids, rfids);
    }

    /**
     * 布草打扎
     *
     * @param rfidsVO
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/pack", method = RequestMethod.POST)
    @ResponseBody
    public Result pack(@RequestBody(required = false) RfidsVO rfidsVO, HttpServletRequest request) {
        if (rfidsVO == null) return Result.paramNull();
        return linenService.pack(getToken(request), rfidsVO);

    }

    /**
     * 类别列表
     *
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/types", method = RequestMethod.POST)
    @ResponseBody
    public Result types(HttpServletRequest request) {

        return linenService.types(getToken(request));
    }

    /**
     * 根据rfid查询未处理的打扎单
     *
     * @param rfidsVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/packbyrfids", method = RequestMethod.POST)
    @ResponseBody
    public Result types(@RequestBody(required = false) RfidsVO rfidsVO, HttpServletRequest request) throws BusinessException {
        String token = getToken(request);
        if (rfidsVO.getRfids() == null || rfidsVO.getRfids().size() == 0) {
            throw new BusinessException("00001", "请传入rfid");
        }
        LinenPack pack = this.linenService.findPacksByRfids(token, rfidsVO.getRfids());
        return new Result(pack);

    }

    /**
     * 根据rfids 查询sku详情
     *
     * @param rfidsVO
     * @param request
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/skubyrfid", method = RequestMethod.POST)
    @ResponseBody
    public Result querySkuByRfid(@RequestBody(required = true) RfidsVO rfidsVO, HttpServletRequest request) throws BusinessException {
        String token = getToken(request);
        if (rfidsVO.getRfids() == null || rfidsVO.getRfids().size() == 0) {
            throw new BusinessException("00001", "请传入rfid");
        }
        List<SkuInfoEntity> skus = this.linenService.findSkuByRfid(token, rfidsVO.getRfids());
        return new Result(skus);

    }

}
