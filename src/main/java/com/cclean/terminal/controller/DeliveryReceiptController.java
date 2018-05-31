package com.cclean.terminal.controller;

import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.DeliveryReceipt;
import com.cclean.terminal.model.Result;
import com.cclean.terminal.service.DeliveryreceiptService;
import com.cclean.terminal.vo.GenerateVO;
import com.cclean.terminal.vo.IdVO;
import com.cclean.terminal.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 配送单
 */
@RestController
@RequestMapping("deliveryreceipt")
public class DeliveryReceiptController extends BaseController {


    @Autowired
    DeliveryreceiptService deliveryreceiptService;

    /**
     * 生成配送单
     *
     * @param generateVO
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    public Result generate(@RequestBody(required = false) GenerateVO generateVO, HttpServletRequest request) throws BusinessException {

        DeliveryReceipt data = deliveryreceiptService.generate(getToken(request), generateVO);
        return new Result(data);
    }

    /**
     * 配送单查询列表
     *
     * @param orderVO
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Result search(@RequestBody(required = false) OrderVO orderVO, HttpServletRequest request) throws BusinessException {
        if (orderVO.getTime() == null) {
            return new Result("00001", "请传入日期");
        }
        List<DeliveryReceipt> data = deliveryreceiptService.list(getToken(request), orderVO);
        return new Result(data);
    }

    /**
     * 配送单详情
     *
     * @param idVO
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public Result info(@RequestBody(required = false) IdVO idVO, HttpServletRequest request) throws BusinessException {
        DeliveryReceipt data = deliveryreceiptService.info(getToken(request), idVO);
        return new Result(data);
    }

}
