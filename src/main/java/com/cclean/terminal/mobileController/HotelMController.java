package com.cclean.terminal.mobileController;

import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.mobileService.HotelMService;
import com.cclean.terminal.model.Result;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.vo.HotelVO;
import com.cclean.terminal.vo.IdVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yulq
 * @create 2018-04-12 17:57
 * @desc
 **/
@RequestMapping("/mobile/hotel")
@RestController
public class HotelMController extends BaseMController {

    @Resource
    private HotelMService hotelMService;

    /**
     * 酒店列表-简单信息
     *
     * @param hotelVO
     * @param request
     * @return
     */
    @PostMapping(value = "/list")
    public Result hotels(@RequestBody HotelVO hotelVO, HttpServletRequest request) throws BusinessException {
        if (hotelVO == null || hotelVO.getPageNum() == null || hotelVO.getPageSize() == null) {
            throw  new BusinessException("00001", "分页参数不足");
        }
        String token = getToken(request);
        String factoryIds = request.getHeader("factoryIds");
        if (factoryIds == null || StringUtils.isBlank(factoryIds)) {
            throw new BusinessException("00001","用户权限为空：factoryIds:"+factoryIds);
        }
        String[] split = factoryIds.split(",");
        hotelVO.setFactoryIds(Arrays.asList(split));
        return new Result(hotelMService.list(token, hotelVO));
    }

    /**
     * 酒店详情
     *
     * @param idVO
     * @param request
     * @return
     */
    @PostMapping(value = "/info")
    public Result hotel(@RequestBody IdVO idVO, HttpServletRequest request) throws BusinessException {
        String token = getToken(request);
        return new Result(hotelMService.getOne(token, idVO.getId()));
    }

    /**
     * 根据酒店id获取配送点
     *
     * @param idVO
     * @param request
     * @return
     */
    @PostMapping("/hpoints")
    public Result getPointsByhotelId(@RequestBody IdVO idVO, HttpServletRequest request) throws BusinessException {
        String token = getToken(request);
        return new Result(hotelMService.listpoints(token, idVO.getId()));
    }

}
