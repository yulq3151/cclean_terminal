package com.cclean.terminal.controller;

import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.Result;
import com.cclean.terminal.model.WorkOrder;
import com.cclean.terminal.service.WorkoderService;
import com.cclean.terminal.vo.IdVO;
import com.cclean.terminal.vo.OrderIdsVO;
import com.cclean.terminal.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 任务单
 */
@RestController
@RequestMapping("workorder")
public class WorkoderController extends BaseController {


    @Autowired
    WorkoderService workoderService;

    /**
     * 生成任务单
     *
     * @param orderIdsVO
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    public Result generate(@RequestBody(required = false) OrderIdsVO orderIdsVO, HttpServletRequest request) throws BusinessException {
        Result<WorkOrder> result = new Result();
        result.setData(workoderService.generate(getToken(request), orderIdsVO));
        result.setRetCode("00000");
        result.setRetInfo("操作成功");
        return result;
    }

    /**
     * 未处理任务单列表
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
        List<WorkOrder> data = workoderService.list(getToken(request), orderVO);
        return new Result(data);
    }

    /**
     * 任务单查询列表
     *
     * @param orderVO 参数封装
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
        List<WorkOrder> data = workoderService.list(getToken(request), orderVO);
        return new Result(data);
    }

    /**
     * 任务单详情
     *
     * @param idVO
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public Result info(@RequestBody(required = false) IdVO idVO, HttpServletRequest request) throws BusinessException {

        WorkOrder data = workoderService.info(getToken(request), idVO);
        return new Result(data);
    }

}
