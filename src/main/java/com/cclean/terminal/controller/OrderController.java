package com.cclean.terminal.controller;

import com.cclean.terminal.constant.Constant;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.Order;
import com.cclean.terminal.model.Result;
import com.cclean.terminal.service.OrderService;
import com.cclean.terminal.vo.IdVO;
import com.cclean.terminal.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订单相关
 */
@RestController
@RequestMapping("order")
public class OrderController extends BaseController {

    @Autowired
    OrderService orderService;

    /**
     * 未复核收脏单列表
     *
     * @param
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/unrecheck", method = RequestMethod.POST)
    @ResponseBody
    public Result unrecheck(@RequestBody(required = false) OrderVO orderVO, HttpServletRequest request) throws BusinessException {
        if (orderVO.getHotelId() == null) {
            return new Result(Constant.RET_CODE_PARAM_NULL, "酒店id必填");
        }
        orderVO.setCheckState(0);
        orderVO.setOrderType(1);
        List<Order> data = orderService.list(getToken(request), orderVO);
        return new Result(data);
    }

    /**
     * 未生成任务单订单列表
     *
     * @param
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/untreated", method = RequestMethod.POST)
    @ResponseBody
    public Result untreated(@RequestBody(required = false) OrderVO orderVO, HttpServletRequest request) throws BusinessException {

        orderVO.setState(0);
        List<Order> data = orderService.list(getToken(request), orderVO);
        return new Result(data);
    }

    /**
     * 订单查询列表
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
        List<Order> data = orderService.list(getToken(request), orderVO);
        return new Result(data);
    }

    /**
     * 订单详情
     *
     * @param idVO
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public Result info(@RequestBody(required = false) IdVO idVO, HttpServletRequest request) throws BusinessException {
        Order order = orderService.info(getToken(request), idVO);
        return new Result(order);
    }
}
