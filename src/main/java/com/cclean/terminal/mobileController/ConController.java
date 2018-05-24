package com.cclean.terminal.mobileController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.mobileService.ConService;
import com.cclean.terminal.model.Factory;
import com.cclean.terminal.model.Result;
import com.cclean.terminal.model2.*;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.vo.PageVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yulq
 * @create 2018-04-12 19:23
 * @desc
 **/
@RequestMapping("/mobile")
@RestController
public class ConController extends BaseMController {


    @Resource
    private ConService conService;

    /**
     * 线路列表
     *
     * @param request
     * @param param
     * @return
     */
    @PostMapping(value = "/lines")
    public Result lines(HttpServletRequest request, @RequestBody(required = true) String param) throws BusinessException {
        String token = getToken(request);
        JSONObject jsonObj = JSONObject.parseObject(param);
        String pageNum = jsonObj.getString("pageNum");
        String pageSize = jsonObj.getString("pageSize");
        String state = jsonObj.getString("state");
        String name = jsonObj.getString("name");
        String id = jsonObj.getString("id");
        if (StringUtils.isBlank(pageNum) || StringUtils.isBlank(pageSize)) {
            throw new BusinessException("00001", "分页参数不足");
        }
        PageVO pageVO = new PageVO();
        pageVO.setPageSize(Integer.valueOf(pageSize));
        pageVO.setPageNum(Integer.valueOf(pageNum));
        PageMo pageMo = conService.lines(pageVO, token, state, name, id);
        return new Result(pageMo);
    }

    /**
     * 品牌列表
     *
     * @param request
     * @param param
     * @return
     */
    @PostMapping(value = "/brands")
    public Result brands(HttpServletRequest request, @RequestBody(required = true) String param) throws BusinessException {
        String token = getToken(request);
        JSONObject jsonObj = JSONObject.parseObject(param);
        String pageNum = jsonObj.getString("pageNum");
        String pageSize = jsonObj.getString("pageSize");
        String name = jsonObj.getString("name");
        if (StringUtils.isBlank(pageNum) || StringUtils.isBlank(pageSize)) {
            throw new BusinessException("00001", "分页参数不足");
        }
        PageVO pageVO = new PageVO();
        pageVO.setPageSize(Integer.valueOf(pageSize));
        pageVO.setPageNum(Integer.valueOf(pageNum));
        PageMo pageMo = conService.brands(pageVO, token, name);
        return new Result(pageMo);
    }

    /**
     * 打扎信息
     *
     * @param request
     * @param param
     * @return
     */
    @PostMapping(value = "/linenpack")
    public Result linenpack(HttpServletRequest request, @RequestBody(required = true) String param) throws BusinessException {
        String token = getToken(request);
        JSONObject jsonObj = JSONObject.parseObject(param);
        String id = jsonObj.getString("id");
        if (StringUtils.isBlank(id)) {
            throw new BusinessException("00001", "参数有误");
        }
        LinenPackM packM = conService.pack(token, id);
        return new Result(packM);
    }

    /**
     * 解除打扎(删除/配送）
     *
     * @param request
     * @param param
     * @return
     */
    @PostMapping(value = "/updatelinenpack")
    public Result updatelinenpack(HttpServletRequest request, @RequestBody(required = true) String param) throws BusinessException {
        String token = getToken(request);
        JSONObject jsonObj = JSONObject.parseObject(param);
        List<String> ids = JSONArray.parseArray(jsonObj.getString("ids"), String.class);
        if (ids == null || ids.size() == 0) {
            throw new BusinessException("00001", "参数有误");
        }
        boolean bo = conService.updatepack(token, ids, "2");
        return new Result(bo);
    }

    /**
     * 根据配送点ID查询配送点
     *
     * @param request
     * @param param
     * @return
     * @throws BusinessException
     */
    @PostMapping("/deliveryPoint")
    public Result deliveryPoint(HttpServletRequest request, @RequestBody(required = true) String param) throws BusinessException {
        String token = getToken(request);
        JSONObject jsonObj = JSONObject.parseObject(param);
        String id = jsonObj.getString("id");
        if (StringUtils.isBlank(id)) {
            throw new BusinessException("00001", "参数有误");
        }
        DeliveryPointM deliveryPointM = conService.deliveryPoint(token, id);
        return new Result(deliveryPointM);
    }

    /**
     * 关于我们
     *
     * @return
     */
    @RequestMapping(value = "/aboutUs")
    public Result aboutUs() {
        AboutUsModel model = new AboutUsModel();
        model.setLogoUrl("http://tracker.sydimg.com/group1/M00/D8/B2/wKgEZlK79CewKXbQAADNZgEgNRc064.jpg");
        model.setCompanyBrief("上海超洁智联科技有限公司，成立于2011年，是" +
                "中国第一家专业提供公共纺织品租赁+洗涤服务，整体解决方案的企业。" +
                "目前已投资5000万元在上海宝山区月浦工业区示范工厂，" +
                "通过把国外先进的租赁式洗涤服务模式复制到中国，促进我国的洗涤行业向自动化、节水、节能、环保、高效率方向转变。" +
                "并以打造公共纺织品共享租赁全生态链的B2B平台，为商旅人士提供洁净、舒适的服务为目标。");
        model.setCopyrightMessage("本程序受著作权法和国际条约保护。如未经授权而擅自复制或传播本程序(或其中任何部分)，" +
                "将受到严厉的民事和刑事制裁，并将在法律许可的最大额度内受到起诉。");
        model.setWebsite("www.cclean.cn");
        model.setTelephone("021-5596 2031");
        model.setEmail("chaojie@cclean.cn");
        model.setCopyright("CopyRight @2011-2018上海超洁智联科技有限公司");

        return new Result(model);
    }

    /**
     * 版本更新
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/versionupdate")
    public Result versionUpdate(@RequestBody(required = false) String param, HttpServletRequest request) throws BusinessException {
        String token = getToken(request);
        VersionInfo versionInfo = this.conService.versionUpdate(param, token);
        return new Result(versionInfo);
    }

    /**
     * 查询物流用户
     *
     * @param request
     * @return
     */
    @PostMapping("/user/query")
    public Result findUsersByType(HttpServletRequest request, @RequestBody String param) throws BusinessException {
        String token = getToken(request);
        JSONObject object = JSONObject.parseObject(param);
        int type = object.getIntValue("type");
        int modelType = object.getIntValue("modelType");
        int pageNum = object.getIntValue("pageNum");
        int pageSize = object.getIntValue("pageSize");
        if (pageNum <= 0 || pageSize <= 0) {
            throw new BusinessException("00001","分页参数错误");
        }
        PageMo<UserInfo> users = this.conService.findUsersByType(token, type, modelType, pageNum, pageSize);
        return new Result(users);
    }

    /**
     *  查询工厂列表
     * @param request
     * @param param
     * @return
     * @throws BusinessException
     */
    @PostMapping("/factory/query")
    public Result findFactory(HttpServletRequest request, @RequestBody String param) throws BusinessException {
        JSONObject object = JSONObject.parseObject(param);
        int type = object.getIntValue("type");
        List<String> factoryIds = JSONArray.parseArray(object.getString("factoryIds"), String.class);
        List<Factory> factorys = this.conService.findFactorys(type, factoryIds);
        return new Result(factorys);
    }

}
