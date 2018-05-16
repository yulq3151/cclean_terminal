package com.cclean.terminal.controller;

import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.config.Result;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model2.VersionInfo;
import com.cclean.terminal.service.*;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.vo.HotelVO;
import com.cclean.terminal.vo.PageVO;
import com.cclean.terminal.vo.PointVO;
import com.cclean.terminal.vo.RfidsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 不好分类的接口，如基础信息中品牌、供应商、产权、报废原因等
 */
@RestController
@RequestMapping("/")
public class CommonController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    BrandService brandService;

    @Autowired
    SupplierService supplierService;

    @Autowired
    PropertyService propertyService;

    @Autowired
    HotelService hotelService;

    @Autowired
    FactoryService factoryService;

    @Autowired
    LinenService linenService;

    @Autowired
    ScrapService scrapService;

    @Resource
    private DeliverylineService deliverylineService;

    /**
     * 品牌列表
     *
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/brands", method = RequestMethod.POST)
    @ResponseBody
    public Result brands(HttpServletRequest request) {

        return brandService.brands(getToken(request));
    }

    /**
     * 供应商列表
     *
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/suppliers", method = RequestMethod.POST)
    @ResponseBody
    public Result suppliers(HttpServletRequest request) {

        return supplierService.suppliers(getToken(request));
    }

    /**
     * 产权所有者列表
     *
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/propertys", method = RequestMethod.POST)
    @ResponseBody
    public Result propertys(HttpServletRequest request) {

        return propertyService.propertys(getToken(request));
    }

    /**
     * 酒店列表
     *
     * @param hotelVO
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/hotels", method = RequestMethod.POST)
    @ResponseBody
    public Result hotels(@RequestBody(required = false) HotelVO hotelVO, HttpServletRequest request) throws BusinessException {
        if (hotelVO == null) return Result.paramNull();
        logger.info(" hotels hotelVO value is:" + hotelVO);

        return hotelService.hotels(getToken(request), hotelVO);
    }

    /**
     * 配送点列表
     *
     * @param
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/deliverypoints", method = RequestMethod.POST)
    @ResponseBody
    public Result deliverypoints(@RequestBody(required = false) PointVO pointVO, HttpServletRequest request) throws BusinessException {
        if (pointVO == null) return Result.paramNull();
        logger.info(" deliverypoints pointVO value is:" + pointVO);

        return hotelService.deliverypoints(getToken(request), pointVO);

    }

    /**
     * 工厂列表
     *
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/factorys", method = RequestMethod.POST)
    @ResponseBody
    public Result factorys(@RequestBody(required = false) PageVO pageVO, HttpServletRequest request) {
        if (pageVO == null) return Result.paramNull();
        logger.info(" factorys pointVO value is:" + pageVO);

        return factoryService.factorys(getToken(request), pageVO);
    }

    /**
     * 布草列表
     *
     * @param rfidsVO
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/linens", method = RequestMethod.POST)
    @ResponseBody
    public Result linens(@RequestBody(required = false) RfidsVO rfidsVO, HttpServletRequest request) throws BusinessException {
        if (rfidsVO == null) return Result.paramNull();
        logger.info(" linens rfidsVO value is:" + rfidsVO);

        return linenService.linens(getToken(request), rfidsVO);

    }

    /**
     * 报废原因列表
     *
     * @param pageVO
     * @return
     * @throws BusinessException
     */
    // RequestBody的属性required，为true代表所有参数必填，为false非必填，参数由自己判断
    @RequestMapping(value = "/scrapreason", method = RequestMethod.POST)
    @ResponseBody
    public Result scrapreason(@RequestBody(required = false) PageVO pageVO, HttpServletRequest request) {
        if (pageVO == null) return Result.paramNull();
        logger.info(" scrapreason pageVO value is:" + pageVO);

        return scrapService.scrapreason(getToken(request), pageVO);
    }


    /**
     * 获取配送线路
     *
     * @param pageVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/deliverylines", method = RequestMethod.POST)
    @ResponseBody
    public com.cclean.terminal.model.Result getdeliveryline(@RequestBody(required = false) PageVO pageVO, HttpServletRequest request) {
        try {
            PageMo pageMo = deliverylineService.getdeliverylines(getToken(request), pageVO);
            return new com.cclean.terminal.model.Result(pageMo);
        } catch (BusinessException e) {
            return new com.cclean.terminal.model.Result(e.getErrorCode(), e.getMessage());
        }
    }

    /**
     * 版本更新
     *
     * @param param
     * @param request
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/version", method = RequestMethod.POST)
    @ResponseBody
    public com.cclean.terminal.model.Result version(@RequestBody(required = false) String param, HttpServletRequest request) throws BusinessException {
        String token = getToken(request);
        if (!param.contains("versionCode")) {
            return new com.cclean.terminal.model.Result("00001", "参数有误！请重试");
        }
        JSONObject object = JSONObject.parseObject(param);
        String code = object.getString("versionCode");
        if (StringUtils.isBlank(code)) {
            return new com.cclean.terminal.model.Result("00001", "未获取到当前版本号");
        }
        VersionInfo versionInfo = this.propertyService.versionUpdate(param, token);
        Map<String, String> map = new HashMap<>();
        map.put("versionid", versionInfo.getVersionid());
        map.put("versioncode", versionInfo.getVersioncode());
        map.put("versionname", versionInfo.getVersionname());
        map.put("url", versionInfo.getUrl());
        map.put("title", versionInfo.getTitle());
        map.put("content", versionInfo.getContent());
        return new com.cclean.terminal.model.Result(map);
    }


}
