package com.cclean.terminal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cclean.terminal.config.Result;
import com.cclean.terminal.constant.Constant;
import com.cclean.terminal.entity.TMenu;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.*;
import com.cclean.terminal.model2.AuthorityDetailList;
import com.cclean.terminal.model2.UserInfo;
import com.cclean.terminal.service.FactoryService;
import com.cclean.terminal.service.UserService;
import com.cclean.terminal.util.FastJsonUtil;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.vo.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 用户操作相关逻辑
 */
@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Value("${invoke.users.list}")
    private String userslisturl;

    @Resource
    private FactoryService factoryService;

    /**
     * 终端登录操作，检查用户，检查权限
     *
     * @param loginVO
     * @param response
     * @return 用户信息和菜单及工厂
     * @throws BusinessException
     */
    @Override
    public Result login(LoginVO loginVO, HttpServletResponse response) throws BusinessException {
        Result result = Result.success();
        String loginName = loginVO.getLoginName();
        String password = loginVO.getPassword();
        System.out.println("业务参数" + loginName);

        String url = cloudUrl + "/cloud/user/center/user/login";
        String js = JSONArray.toJSONString(loginVO);
        JSONObject jsonParam = JSONArray.parseObject(js);
        String json = HttpUtil.doPost(url, "", jsonParam);
        logger.info("userinfo：respose:{}", json);
        JSONObject jsonObject1 = JSONObject.parseObject(json);
        String retCode = jsonObject1.getString("retCode");
        if (!retCode.equals("00000")) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        UserInfo userInfo = JSONObject.parseObject(jsonObject1.getString("data"), UserInfo.class);
        if (userInfo == null) {
            result.setCodeInfo("10002", "用户信息错误");
            return result;
        }
        url = cloudUrl + "/cloud/user/center/user/authority";
        jsonParam = new JSONObject();
        json = HttpUtil.doPost(url, userInfo.getId(), jsonParam);
        logger.info("用户权限：respose:{}", json);
        Type powerType = new TypeReference<ResultData<UserAuthority>>() {
        }.getType();
        ResultData<UserAuthority> powerResultData = FastJsonUtil.toObject(json, powerType);
        if (!powerResultData.getRetCode().equals(Constant.RET_CODE_SUCCESS)) {
            result.setCodeInfo(powerResultData.getRetCode(), powerResultData.getRetInfo());
            return result;
        }
        UserAuthority userAuthority = powerResultData.getData();
        if (userAuthority == null) {
            result.setCodeInfo("10003", "权限信息错误");
            return result;
        }
        List<TMenu> menus = new ArrayList<>();
        List<String> menuNames = new ArrayList<>();
        Map<String, AuthorityModel> userInfoMap = userAuthority.getAuthorityInfo();
        if (userAuthority == null || userInfoMap.size() <= 0) {
            result.setCodeInfo(Constant.RET_CODE_OBJ_NULL, Constant.RET_INFO_OBJ_NULL);
            return result;
        }
        List<String> fids = new ArrayList<>();
        if (userInfoMap.containsKey("terminalDev")&&userInfoMap.get("terminalDev")!=null) {
            AuthorityModel authorityModel = userInfoMap.get("terminalDev");
            TMenu tMenu = authorityModel.getRoleMenu();
            if (tMenu != null) {
                List<TMenu> tMenus = tMenu.gettMenuList();
                if (tMenus != null && tMenus.size() > 0) {
                    for (TMenu menu : tMenus) {
                        menus.add(menu);
                        menuNames.add(menu.getCode());
                    }
                }
            }
            List<AuthorityDetail> authList = authorityModel.getAuthorityDetailList();
            if (authList != null && authList.size() > 0) {
                for (int i = 0; i < authList.size(); i++) {
                    AuthorityDetail auth = authList.get(i);
                    if (auth.getType() == 1) {
                        fids = auth.getContent();
                        userInfo.setFactoryid(fids);
                        break;
                    }
                }
            }else {
                result.setCodeInfo(Constant.RET_CODE_DEBUG, "未找到用户所属工厂，请联系管理员");
                return result;
            }
        } else {
            result.setCodeInfo(Constant.RET_CODE_DEBUG, "用户没有终端权限");
            return result;
        }
        List<Factory> factories = this.factoryService.findFactorysByids(userInfo.getId(), fids);

        Map<String, Object> map = new HashMap<>();
        map.put("user", userInfo);
        if (factories != null && factories.size() > 0) {
            map.put("factory", factories.get(0));
        } else {
            map.put("factory", null);
        }
        map.put("menus", menuNames);

        // 如果登陆通过，header返回token
        response.setHeader("token", userInfo.getId());
        // 如果登陆通过 body返回菜单
        result.setData(map);
        return result;
    }

    /**
     * 登出
     *
     * @param token
     * @param response
     * @throws BusinessException
     */
    @Override
    public void logout(String token, HttpServletResponse response) throws BusinessException {
        if (token.isEmpty()) {
            // 直接抛出，全局异常捕捉，接口不需要单独维护错误返回
            throw new BusinessException("00005", "token为空");
        }
        // 如果退出成功，返回消息ok
        response.setHeader("msg", "ok");
    }

    /**
     * 配货app用户登录
     *
     * @param loginVO
     * @return 用户信息
     * @throws BusinessException
     */
    @Override
    public UserInfo mobilelogin(LoginVO loginVO) throws BusinessException {
        String url = cloudUrl + "/cloud/user/center/user/login";
        JSONObject param = new JSONObject();
        param.put("loginName", loginVO.getLoginName());
        param.put("password", loginVO.getPassword());
        String httpEntitys = HttpUtil.doPost(url, "", param);
        logger.info("userinfo：respose:{}", httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!retCode.equals("00000")) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        UserInfo userInfo = JSONObject.parseObject(jsonObject1.getString("data"), UserInfo.class);
        //权限校验
        String url1 = cloudUrl + "/cloud/user/center/user/authority";
        //token 为用户的ID
        String httpEntitys1 = HttpUtil.doPost(url1, userInfo.getId(), param);
        logger.info("用户权限：respose:{}", httpEntitys);
        JSONObject userauth = JSONObject.parseObject(httpEntitys1);
        JSONObject data = userauth.getJSONObject("data");
        JSONObject auths = data.getJSONObject("authorityInfo");
        if (auths == null) {
            throw new BusinessException("00001", "用户权限为空");
        }
        JSONObject terminalDev = auths.getJSONObject("deliveryApp");
        if (terminalDev == null) {
            throw new BusinessException("00001", "用户没有配货app的权限");
        }
        String detailList1 = terminalDev.getString("authorityDetailList");
        if (detailList1 == null) {
            throw new BusinessException("00001", "用户配货app的权限为空");
        }
        List<AuthorityDetailList> lists = JSONObject.parseArray(detailList1, AuthorityDetailList.class);
        for (int i = 0; i < lists.size(); i++) {
            AuthorityDetailList detailList = lists.get(i);
            if (detailList.getType() == 1) {
                userInfo.setFactoryid(detailList.getContent());
                break;
            }
        }
        if (userInfo.getFactoryid() == null) {
            throw new BusinessException("00001", "没有找到终端用户对应的工厂");
        }

        List<String> menus = new ArrayList<>();
        JSONObject roleMenu = terminalDev.getJSONObject("roleMenu");
        menuToMenuList(menus,roleMenu);
        userInfo.setMenus(menus);
        return userInfo;
    }

    /**
     * 根据用户ID查询，返回map对象，key是用户ID，value是用户名称
     *
     * @param ids
     * @param token
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, String> findUsersByIds(Set<String> ids, String token) throws BusinessException {
        Map<String, String> map = new HashMap<>();
        if (ids == null || ids.isEmpty()) {
            return map;
        }
        String url = cloudUrl + userslisturl;
        JSONObject param = new JSONObject();
        param.put("idList", ids);
        String httpEntitys = HttpUtil.doPost(url, token, param);
        logger.info("用户列表：respose:{}", httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!retCode.equals("00000")) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        List<UserInfo> userInfos = JSONObject.parseArray(jsonObject1.getString("data"), UserInfo.class);
        for (int i = 0; i < userInfos.size(); i++) {
            UserInfo user = userInfos.get(i);
            map.put(user.getId(), user.getRealName());
        }
        return map;
    }

    private List<String> menuToMenuList(List<String> list,JSONObject menu){
        if (menu == null) return list;
        list.add(menu.getString("code"));
        JSONArray menuList = menu.getJSONArray("tMenuList");
        if(menuList != null && menuList.size() > 0){
            for (int i = 0; i < menuList.size(); i++) {
                JSONObject tMenu1 = menuList.getJSONObject(i);
                menuToMenuList(list,tMenu1);
            }
        }
        return  list;
    }
}
