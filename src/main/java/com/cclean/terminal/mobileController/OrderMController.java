package com.cclean.terminal.mobileController;

import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.controller.BaseController;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.mobileService.OrderMService;
import com.cclean.terminal.model.Result;
import com.cclean.terminal.model2.DeliveryOrder;
import com.cclean.terminal.model2.Order;
import com.cclean.terminal.model2.PickorderOrder;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.vo.OrderIdsVO;
import com.cclean.terminal.vo.OrderVO;
import com.cclean.terminal.vo.ZPickVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yulq
 * @create 2018-04-12 21:07
 * @desc
 **/
@RestController
@RequestMapping("/mobile")
public class OrderMController extends BaseController {


    @Resource
    private OrderMService orderMService;

    /**
     * 订单列表
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "/order/list", method = RequestMethod.POST)
    @ResponseBody
    public Result orderlist(HttpServletRequest request, @RequestBody(required = false) OrderVO orderVO) throws BusinessException {
        String token = getToken(request);
        if (orderVO.getPageNum() == null || orderVO.getPageSize() == null) {
            throw  new BusinessException("00001", "分页参数不足");
        }
        PageMo list = orderMService.orderlist(token, orderVO);
        return new Result(list);
    }

    /**
     * 订单详情
     *
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/order/info", method = RequestMethod.POST)
    public Result orderinfo(HttpServletRequest request, @RequestBody(required = false) String param) throws BusinessException {
        String token = getToken(request);
        JSONObject jsonparam = JSONObject.parseObject(param);
        String id = jsonparam.getString("id");
        if (StringUtils.isBlank(id)) {
            throw  new BusinessException("00001", "分页参数不足");
        }
        Order order = orderMService.orderinfo(token, id);
        return new Result(order);

    }

    /**
     * 订单生成任务单
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "/createPickOrder", method = RequestMethod.POST)
    public Result createPickOrder(HttpServletRequest request, @RequestBody(required = false) OrderIdsVO orderIdsVO) throws BusinessException {
        String token = getToken(request);
        if (orderIdsVO == null) {
            throw  new BusinessException("00001", "分页参数不足");
        }
        List<String> ids = orderIdsVO.getOrderIds();
        if (ids.isEmpty() || ids.size() == 0) {
            throw  new BusinessException("00001", "订单不能为空：orderIdsVo:"+orderIdsVO.toString());
        }
        PickorderOrder pickOrder = orderMService.createPickOrder(token, orderIdsVO);
        return new Result(pickOrder);

    }


    /**
     * 任务单列表
     *
     * @param request
     * @param orderVO
     * @return
     */
    @RequestMapping(value = "/pickorder/list", method = RequestMethod.POST)
    @ResponseBody
    public Result pickorderlist(HttpServletRequest request, @RequestBody(required = false) OrderVO orderVO) throws BusinessException {
        String token = getToken(request);
        if (orderVO.getPageNum() == null || orderVO.getPageSize() == null) {
            throw  new BusinessException("00001", "分页参数不足");
        }
        PageMo list = orderMService.pickorderlist(token, orderVO);
        return new Result(list);
    }

    /**
     * 任务单详情
     *
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/pickorder/info", method = RequestMethod.POST)
    public Result pickorderinfo(HttpServletRequest request, @RequestBody(required = false) String param) throws BusinessException {
        String token = getToken(request);
        JSONObject jsonparam = JSONObject.parseObject(param);
        String id = jsonparam.getString("id");
        if (StringUtils.isBlank(id)) {
            throw  new BusinessException("00001", "id不能为空");
        }
        PickorderOrder pickorderinfo = orderMService.pickorderinfo(token, id);
        return new Result(pickorderinfo);

    }

    /**
     * 任务单生成配送单
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "/createDeliveryOrder", method = RequestMethod.POST)
    public Result createDeliveryOrder(HttpServletRequest request, @RequestBody(required = false) ZPickVo zPickVo) throws BusinessException {
        String token = getToken(request);
        String workid = zPickVo.getWorkOrderId();
        if (StringUtils.isBlank(workid) || zPickVo.getSkuSVos() == null || zPickVo.getPackids() == null) {
            throw  new BusinessException("00001", "参数不完整：workid:"+workid+",skuSVos:"+zPickVo.getSkuSVos().toString()+",Packids:"+zPickVo.getPackids());
        }
        if (zPickVo.getSkuSVos().length == 0 || zPickVo.getPackids().length == 0) {
            throw  new BusinessException("00001","打扎信息不完整 skuSVos:"+zPickVo.getSkuSVos()+",Packids:"+zPickVo.getPackids());
        }
        DeliveryOrder deliveryOrder = orderMService.createDeliveryOrder(token, zPickVo);
        return new Result(deliveryOrder);

    }

    /**
     * 我的配送单
     *
     * @param request
     * @param orderVO
     * @return
     */
    @RequestMapping(value = "/mydeliorder/list", method = RequestMethod.POST)
    @ResponseBody
    public Result myDeliveryorderlist(HttpServletRequest request, @RequestBody(required = false) OrderVO orderVO) throws BusinessException {
        String token = getToken(request);
        if (orderVO.getPageNum() == null || orderVO.getPageSize() == null) {
            throw  new BusinessException("00001", "分页参数不足");
        }
        PageMo pageMo = orderMService.myDeliveryorderlist(token, orderVO);
        return new Result(pageMo);
    }


    /**
     * 配送单列表
     *
     * @param request
     * @param orderVO
     * @return
     */
    @RequestMapping(value = "/deliveryorder/list", method = RequestMethod.POST)
    @ResponseBody
    public Result deliveryorderlist(HttpServletRequest request, @RequestBody(required = false) OrderVO orderVO) throws BusinessException {
        String token = getToken(request);
        if (orderVO.getPageNum() == null || orderVO.getPageSize() == null) {
            throw  new BusinessException("00001", "分页参数不足");
        }
        PageMo pageMo = orderMService.deliveryorderlist(token, orderVO);
        return new Result(pageMo);
    }


    /**
     * 配送单详情
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "/deliveryorder/info", method = RequestMethod.POST)
    public Result deliveryOrderinfo(HttpServletRequest request, @RequestBody(required = false) String param) throws BusinessException {
        String token = getToken(request);
        JSONObject jsonparam = JSONObject.parseObject(param);
        String id = jsonparam.getString("id");
        if (StringUtils.isBlank(id)) {
            throw  new BusinessException("00001", "id不能为空");
        }
        DeliveryOrder deliveryOrder = orderMService.deliveryOrderinfo(token, id);
        return new Result(deliveryOrder);

    }

}
