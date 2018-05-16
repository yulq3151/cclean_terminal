package com.cclean.terminal.controller;

import com.cclean.terminal.config.Result;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.service.SkuService;
import com.cclean.terminal.vo.RfidsVO;
import com.cclean.terminal.vo.SkuVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Sku相关接口
 */
@RestController
@RequestMapping("sku")
public class SkuController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(SkuController.class);

    @Autowired
    SkuService skuService;

    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    @ResponseBody
    public Result statistics(@RequestBody(required = false) RfidsVO rfidsVO,HttpServletRequest request) throws BusinessException {
            if (rfidsVO == null) return Result.paramNull();
            logger.info(" statistics rfidsVO value is:" + rfidsVO);

            return skuService.statistics(getToken(request),rfidsVO);
    }

    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Result search(@RequestBody(required = false) SkuVO skuVO,HttpServletRequest request) {
        if (skuVO == null) return Result.paramNull();
        logger.info(" search skuVO value is:" + skuVO);
            return  skuService.page(getToken(request),skuVO);
    }

}
