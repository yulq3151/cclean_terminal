package com.cclean.terminal.mobileController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.config.Result;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.mobileService.CagecarService;
import com.cclean.terminal.model2.Cagecar;
import com.cclean.terminal.model2.LinenPackM;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yulq
 * @create 2018-05-23 11:10
 * @desc 笼车
 **/
@RestController
@RequestMapping("/cagecar")
public class CagecarController extends BaseMController {

    @Resource
    private CagecarService cagecarService;

    /**
     * 笼车登记
     *
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("/register")
    public Result register(HttpServletRequest request, @RequestBody String param) throws BusinessException {
        String token = getToken(request);
        JSONObject obj = JSONArray.parseObject(param);
        List<String> codes = JSONArray.parseArray(obj.getString("codes"), String.class);
        if (codes == null || codes.size() == 0) {
            throw new BusinessException("00001", "参数不足");
        }
        String factoryId = obj.getString("factoryId");
        boolean bl = this.cagecarService.register(token, codes, factoryId);
        return new Result(bl);
    }

    /**
     * 笼车查询
     *
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("/list")
    public Result list(HttpServletRequest request, @RequestBody String param) throws BusinessException {
        String token = getToken(request);
        JSONObject obj = JSONArray.parseObject(param);
        List<String> codes = JSONArray.parseArray(obj.getString("codes"), String.class);
        List<Cagecar> list = this.cagecarService.list(token, codes);
        return new Result(list);
    }

    /**
     * 笼车使用
     *
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("/borrow")
    public Result borrow(HttpServletRequest request, @RequestBody String param) throws BusinessException {
        String token = getToken(request);
        JSONObject obj = JSONArray.parseObject(param);
        List<String> codes = JSONArray.parseArray(obj.getString("codes"), String.class);
        String userId = obj.getString("userId");
        String factoryId = obj.getString("factoryId");
        int status = obj.getIntValue("status");
        List<String> packCodes = JSONArray.parseArray(obj.getString("packCodes"), String.class);
        if (codes == null || codes.isEmpty()) {
            throw new BusinessException("00001", "请传入笼车code");
        }
        boolean bl = this.cagecarService.cagecarUse(token, status, userId, factoryId, codes, packCodes);
        return new Result(bl);
    }

    /**
     * 笼车货物明细
     *
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("/packs")
    public Result findPacksByCode(HttpServletRequest request, @RequestBody String param) throws BusinessException {
        String token = getToken(request);
        JSONObject obj = JSONArray.parseObject(param);
        String code = obj.getString("code");
        List<LinenPackM> packs = this.cagecarService.findPacks(token, code);
        return new Result(packs);
    }

}
