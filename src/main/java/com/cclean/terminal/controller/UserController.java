package com.cclean.terminal.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.Result;
import com.cclean.terminal.model2.UserInfo;
import com.cclean.terminal.service.UserService;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.util.Utils;
import com.cclean.terminal.vo.LoginVO;
import com.cclean.terminal.vo.LoginVO2;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户接口，登陆和注销
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    // 仅支持post，全接口采用post
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public com.cclean.terminal.config.Result login(@RequestBody(required = false) LoginVO loginVO, HttpServletResponse response) throws BusinessException {
        if (loginVO == null) return com.cclean.terminal.config.Result.paramNull();
        return userService.login(loginVO, response);
    }

    //退出登录
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> logout(@RequestBody(required = false) String token, HttpServletResponse response) throws BusinessException {
        // 验证参数
        Utils.paramIsNull(token);
        // 退出登陆
        userService.logout(token, response);
        Map<String, Object> result = new HashMap<>();
        result.put("retCode", "00000");
        result.put("retInfo", "操作成功");
        return result;
    }


    // 转发测试
    @RequestMapping(value = "/relogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> reLogin(@RequestBody(required = false) LoginVO2 loginVO, HttpServletRequest request, HttpServletResponse response) throws BusinessException, IOException {
        // 转发
        String url = "http://localhost:8091/terminal/api/user/login";
        Map<String, Object> result = new HashMap<>();
        result.put("retCode", "00000");
        result.put("retInfo", "操作成功");
        String js = JSONArray.toJSONString(loginVO);
        JSONObject params = JSONArray.parseObject(js);
        params.put("loginName", params.get("username"));
        params.remove("username");
        System.out.println("参数" + params.toJSONString());
        CloseableHttpResponse httpResponse = HttpUtil.doPost(url, request, params.toJSONString());
        if (httpResponse != null) {
            Header[] headers = httpResponse.getAllHeaders();
            for (Header header : headers) {
                if ("token".equals(header.getName())) {
                    response.setHeader(header.getName(), header.getValue());
                }
            }
            HttpEntity entity = httpResponse.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");
            JSONObject jsonObject = JSONArray.parseObject(json);
            result.putAll((Map) jsonObject);
            System.out.println("转发" + json);
        }
        // 返回
        return result;
    }

    /**
     * 配货app 登录
     *
     * @param request
     * @param loginVO
     * @param response
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/mobile/login", method = RequestMethod.POST)
    @ResponseBody
    public Result Mobilelogin(HttpServletRequest request, @RequestBody(required = true) LoginVO loginVO, HttpServletResponse response) throws BusinessException {
        String account = loginVO.getLoginName();
        String pwd = loginVO.getPassword();
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(pwd)) {
            return new Result("00002", "用户名或密码为空");
        }
        //获取用户信息
        UserInfo userInfo = this.userService.mobilelogin(loginVO);
        if (userInfo.getStatus().equals("2")) {
            return new Result("此用户不存在");
        }
        //若有权限则返回
        response.setHeader("token", userInfo.getId());

        return new Result(userInfo);
    }

    /**
     * 配货app 登出
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/mobile/logout", method = RequestMethod.POST)
    @ResponseBody
    public void MobileLogout(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("token", "");
    }

}
