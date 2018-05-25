package com.cclean.terminal.controller;

import com.cclean.terminal.config.Result;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.SkuReceived;
import com.cclean.terminal.service.SkuService;
import com.cclean.terminal.vo.RfidsVO;
import com.cclean.terminal.vo.SkuVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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

    /**
     * 布草收脏统计
     * @param rfidsVO
     * @param request
     * @return
     * @throws BusinessException
     */
    @PostMapping("/recvstatistics")
    public Result recvstatistics(@RequestBody RfidsVO rfidsVO,HttpServletRequest request) throws BusinessException {
        String token = getToken(request);
        List<String> rfids = rfidsVO.getRfids();
        if (rfids == null || rfids.size()==0){
            return new Result("00001","请传入rfids");
        }
        SkuReceived received = this.skuService.recvstatistics(token, rfids);
        return new Result(received);
    }

    /**
     * 布草sku信息查询
     * @param rfidsVO
     * @param request
     * @return
     * @throws BusinessException
     */
    @PostMapping("/review")
    public Result findSkuByrfid(@RequestBody RfidsVO rfidsVO,HttpServletRequest request) throws BusinessException {
        String token = getToken(request);
        List<String> rfids = rfidsVO.getRfids();
        if (rfids == null || rfids.size()==0){
            return new Result("00001","请传入rfids");
        }
        List<Map<String, String>> rfides = this.skuService.findSkuByRfid(token, rfids);
        return new Result(rfides);
    }
}
