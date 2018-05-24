package com.cclean.terminal.mobileController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.config.Result;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.mobileService.CagecarService;
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
     *  笼车登记
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("/register")
    public Result register(HttpServletRequest request,@RequestBody String param) throws BusinessException {
        String token = getToken(request);
        JSONObject obj = JSONArray.parseObject(param);
        List<String> codes = JSONArray.parseArray(obj.getString("codes"), String.class);
        if (codes == null || codes.size()==0) {
            throw new BusinessException("00001","参数不足");
        }
        return null;
    }

    /**
     *  笼车查询
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("/list")
    public Result list(HttpServletRequest request,@RequestBody String param) throws BusinessException {
        String token = getToken(request);
        JSONObject obj = JSONArray.parseObject(param);
        List<String> codes = JSONArray.parseArray(obj.getString("codes"), String.class);
        if (codes == null || codes.size()==0) {
            throw new BusinessException("00001","参数不足");
        }
        return null;
    }

    /**
     *  笼车使用
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("/borrow")
    public Result borrow(HttpServletRequest request,@RequestBody String param) throws BusinessException {
        String token = getToken(request);
        JSONObject obj = JSONArray.parseObject(param);
        List<String> codes = JSONArray.parseArray(obj.getString("codes"), String.class);
        String userId = obj.getString("userId");
        String factoryId = obj.getString("factoryId");
        int status = obj.getIntValue("status");
        if (codes == null || codes.size()==0) {
            throw new BusinessException("00001","参数不足");
        }
        return null;
    }

    /**
     *  笼车装货
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("/load")
    public Result load(HttpServletRequest request,@RequestBody String param) throws BusinessException {
        String token = getToken(request);
        JSONObject obj = JSONArray.parseObject(param);
        List<String> codes = JSONArray.parseArray(obj.getString("codes"), String.class);
        String userId = obj.getString("userId");
        String factoryId = obj.getString("factoryId");
        int status = obj.getIntValue("status");
        if (codes == null || codes.size()==0) {
            throw new BusinessException("00001","参数不足");
        }
        return null;
    }

    /**
     *  笼车货物明细
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("/packs")
    public Result findPacksByCode(HttpServletRequest request,@RequestBody String param) throws BusinessException {
        String token = getToken(request);

        return null;
    }

}
