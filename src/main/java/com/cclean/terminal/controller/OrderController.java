package com.cclean.terminal.controller;

import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.constant.Constant;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.Order;
import com.cclean.terminal.model.Result;
import com.cclean.terminal.service.OrderService;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.vo.IdVO;
import com.cclean.terminal.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    public Result unrecheck(@RequestBody String param, HttpServletRequest request) throws BusinessException {
        String token = getToken(request);
        JSONObject obj = JSONObject.parseObject(param);
        String hotelId = obj.getString("hotelId");
        if (StringUtils.isBlank(hotelId)) {
            throw new BusinessException("00001", "请选择酒店");
        }
        String pointId = obj.getString("pointId");
        int pageNum = obj.getIntValue("pageNum");
        int pageSize = obj.getIntValue("pageSize");
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize <= 0) {
            pageSize = 50;
        }
        List<String> hotelIds = new ArrayList<>();
        hotelIds.add(hotelId);
        List<String> pointIds = new ArrayList<>();
        if (StringUtils.isNotBlank(pointId)) {
            pointIds.add(pointId);
        }
        List<Order> orders = this.orderService.dirList(token, pageNum, pageSize, hotelIds, pointIds, null, null, 0);
        return new Result(orders);
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
