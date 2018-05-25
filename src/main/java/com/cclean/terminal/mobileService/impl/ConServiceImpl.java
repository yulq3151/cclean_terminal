package com.cclean.terminal.mobileService.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.mobileService.ConService;
import com.cclean.terminal.model.Factory;
import com.cclean.terminal.model.Sku;
import com.cclean.terminal.model2.*;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.util.InvokeUtil;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yulq
 * @create 2018-04-12 20:08
 * @desc
 **/
@Service
public class ConServiceImpl implements ConService {
    private static Logger logger = LoggerFactory.getLogger(ConServiceImpl.class);

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Value("${invoke.deliveryline.page}")
    private String deliverylines;

    @Value("${invoke.linenpack.info}")
    private String linenpackurl;

    @Value("${invoke.deliveryPoint.info}")
    private String deliveryPointurl;

    @Value("${invoke.points.list}")
    private String pointslisturl;

    @Value("${invoke.users.list}")
    private String userslisturl;

    @Value("${invoke.skus.list}")
    private String skuslisturl;


    /**
     * 线路列表
     *
     * @param pageVO 分页
     * @param token  授权号
     * @param state  状态
     * @param name   名称
     * @return
     * @throws BusinessException
     */
    @Override
    public PageMo lines(PageVO pageVO, String token, String state, String name, String id) throws BusinessException {
        String url = linenUrl + deliverylines;
        JSONObject param = new JSONObject();
        param.put("state", state);
        param.put("name", name);
        param.put("id", id);
        param.put("size", pageVO.getPageSize());
        param.put("page", pageVO.getPageNum());
        String httpEntitys = HttpUtil.doPost(url, token, param);
        logger.info("线路列表 Responses: {}", httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        Integer total = jsonObject1.getInteger("total");
        String deliverylines = jsonObject1.getString("deliverylines");
        if (StringUtils.isBlank(deliverylines)) {
            return new PageMo();
        }
        List<DeliverylineM> deliverylineMS = JSONObject.parseArray(deliverylines, DeliverylineM.class);
        PageMo pageMo = new PageMo(deliverylineMS, pageVO, total);
        return pageMo;
    }

    /**
     * 品牌列表
     *
     * @param pageVO 分页
     * @param token  授权码
     * @param name   名称
     * @return
     * @throws BusinessException
     */

    @Override
    public PageMo brands(PageVO pageVO, String token, String name) throws BusinessException {
        String url = linenUrl + "/cloud/manage/v1/brand/page";
        JSONObject param = new JSONObject();
        param.put("name", name);
        param.put("pageSize", pageVO.getPageSize());
        param.put("pageNum", pageVO.getPageNum());
        String httpEntitys = HttpUtil.doPost(url, token, param);
        logger.info("品牌列表 Responses: {}", httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        JSONObject page = jsonObject1.getJSONObject("page");
        if (page == null) {
            return new PageMo();
        }
        Integer total = page.getInteger("total");
        String list = page.getString("list");
        if (StringUtils.isBlank(list)) {
            return new PageMo();
        }
        List<Brands> brands = JSON.parseArray(list, Brands.class);
        PageMo pageMo = new PageMo(brands, pageVO, total);
        return pageMo;

    }

    /**
     * 查询打扎信息
     *
     * @param token 授权码
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public LinenPackM pack(String token, String id) throws BusinessException {
        String url = cloudUrl + linenpackurl;
        JSONObject param = new JSONObject();
        param.put("id", id);
        String httpEntitys = HttpUtil.doPost(url, token, param);
        logger.info("查询打扎信息 Responses: {}", httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        String data = jsonObject1.getString("data");
        if (StringUtils.isBlank(data)) {
            return null;
        }
        LinenPackM packM = JSONObject.parseObject(data, LinenPackM.class);
        return packM;
    }

    /**
     * 修改打扎信息为不可用(删除/配送）
     *
     * @param token 授权码
     * @param ids
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean updatepack(String token, List<String> ids, String type) throws BusinessException {
        String url = cloudUrl + "/linen/api/linen/updatepcak";
        JSONObject param = new JSONObject();
        param.put("ids", ids);
        param.put("type", type); //type 1使用 2解除
        String httpEntitys = HttpUtil.doPost(url, token, param);
        logger.info("修改打扎为不可用 Responses: {}", httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        return true;
    }


    /**
     * 配送点信息
     *
     * @param token
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public DeliveryPointM deliveryPoint(String token, String id) throws BusinessException {
        String url = linenUrl + deliveryPointurl;
        JSONObject param = new JSONObject();
        param.put("id", id);
        String httpEntitys = HttpUtil.doPost(url, token, param);
        logger.info("配送点信息 Responses: {}", httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        String deliveryPoint = jsonObject1.getString("deliveryPoint");
        if (StringUtils.isBlank(deliveryPoint)) {
            return null;
        }
        DeliveryPointM deliveryPointM = JSONObject.parseObject(deliveryPoint, DeliveryPointM.class);
        return deliveryPointM;
    }

    /**
     * 根据配送点ID查询，返回map对象，key是配送点ID，value是配送点本身
     *
     * @param ids
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, DeliveryPointM> findPointsByIds(Set<String> ids) throws BusinessException {
        String url = cloudUrl + pointslisturl;
        JSONObject param = new JSONObject();
        param.put("ids", ids);
        String httpEntitys = HttpUtil.doPost(url, "", param);
        logger.info("根据配送点ID查询 Responses: {}", httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        List<DeliveryPointM> pointMS = JSONObject.parseArray(jsonObject1.getString("data"), DeliveryPointM.class);
        Map<String, DeliveryPointM> map = new HashMap<>();
        for (int i = 0; i < pointMS.size(); i++) {
            DeliveryPointM pointM = pointMS.get(i);
            map.put(pointM.getId(), pointM);
        }
        return map;
    }

    /**
     * 根据配送点ID查询名称，返回map对象，key是配送点ID，value是配送点名称
     *
     * @param ids
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, String> findPointName(Set<String> ids) throws BusinessException {
        String url = cloudUrl + pointslisturl;
        JSONObject param = new JSONObject();
        param.put("ids", ids);
        String httpEntitys = HttpUtil.doPost(url, "", param);
        logger.info("根据配送点ID查询 Responses: {}", httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        List<DeliveryPointM> pointMS = JSONObject.parseArray(jsonObject1.getString("data"), DeliveryPointM.class);
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < pointMS.size(); i++) {
            DeliveryPointM pointM = pointMS.get(i);
            map.put(pointM.getId(), pointM.getName());
        }
        return map;
    }


    /**
     * 版本更新
     *
     * @param param
     * @return
     */
    @Override
    public VersionInfo versionUpdate(String param, String token) throws BusinessException {
        JSONObject jsonObj = JSONObject.parseObject(param);
        // 1、酒店APP 2、物流APP 3.配货APP 4.终端 5一体机
        String type = jsonObj.getString("type");
        if ("mobile".equals(type)) {
            jsonObj.put("versionType", "3");
        } else if ("pad".equals(type)) {
            jsonObj.put("versionType", "5");
        } else {
            logger.error("配货版本更新失败：param:{}", param);
            throw new BusinessException("00001", "获取版本失败");
        }
        String url = cloudUrl + "/cloud/basic/version/latest";
        String httpEntitys = HttpUtil.doPost(url, token, jsonObj);
        logger.info("版本更新 Responses: {}", httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }

        VersionInfo versionInfo = JSONObject.parseObject(jsonObject1.getString("data"), VersionInfo.class);
        return versionInfo;
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
        String url = cloudUrl + userslisturl;
        JSONObject param = new JSONObject();
        param.put("idList", ids);
        String httpEntitys = HttpUtil.doPost(url, token, param);
        logger.info("根据用户ID查询 Responses: {}", httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        List<UserInfo> userInfos = JSONObject.parseArray(jsonObject1.getString("data"), UserInfo.class);
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < userInfos.size(); i++) {
            UserInfo user = userInfos.get(i);
            map.put(user.getId(), user.getRealName());
        }
        return map;
    }

    /**
     * 根据sku ID查询，返回map对象，key是sku ID，value是sku本身
     *
     * @param ids
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, Sku> findSkusByIds(Set<String> ids) throws BusinessException {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }
        String url = cloudUrl + skuslisturl;
        JSONObject param = new JSONObject();
        param.put("ids", ids);
        String httpEntitys = HttpUtil.doPost(url, "", param);
        logger.info("根据sku ID查询 Responses: {}", httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        List<Sku> skuList = JSONObject.parseArray(jsonObject1.getString("data"), Sku.class);
        Map<String, Sku> map = new HashMap<>();
        for (int i = 0; i < skuList.size(); i++) {
            Sku sku = skuList.get(i);
            map.put(sku.getId(), sku);
        }
        return map;
    }

    /**
     * 分布查询类型下的用户
     *
     * @param token
     * @param type
     * @param modelType
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageMo<UserInfo> findUsersByType(String token, int type, int modelType, int pageNum, int pageSize) throws BusinessException {
        String url = cloudUrl + "/cloud/user/center/user/getUserByMultiCondition";
        JSONObject param = new JSONObject();
        param.put("type", type);
        param.put("modelType", modelType);
        param.put("pageNum", pageNum);
        param.put("pageSize", pageSize);
        JSONObject object = InvokeUtil.invokeResult(url, token, param);
        logger.info("分类查询用户 Responses: {}", object);
        int total = object.getIntValue("total");
        List<UserInfo> list = JSONObject.parseArray(object.getString("list"), UserInfo.class);
        return new PageMo<>(list, pageNum, pageSize, total);
    }

    /** 根据工厂ID查询工厂，如果未传ID，则查询全部
     * @param type
     * @return
     */
    @Override
    public List<Factory> findFactorys(int type, List<String> fids) throws BusinessException {
        String url =cloudUrl+"/cloud/basic/factory/list";
        JSONObject param = new JSONObject();
        param.put("type",type);
        param.put("factoryIds",fids);
        String data = InvokeUtil.invokeString(url, "", param);
        List<Factory> list = JSONArray.parseArray(data, Factory.class);
        return list;
    }





}
