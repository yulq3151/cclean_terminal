package com.cclean.terminal.mobileService.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.mobileService.HotelMService;
import com.cclean.terminal.model2.DeliveryPointM;
import com.cclean.terminal.model2.HotelBo;
import com.cclean.terminal.model2.HotelM;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.util.InvokeUtil;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.vo.HotelVO;
import com.cclean.terminal.vo.PageVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yulq
 * @create 2018-04-12 18:05
 * @desc
 **/
@Service
public class HotelMServiceImpl implements HotelMService {

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Value("${invoke.hotels}")
    private String hotelsUrl;

    @Value("${invoke.deliveryPoint}")
    private String deliveryPointsUrl;

    @Value("${invoke.hotel.info}")
    private String hotelInfoUrl;

    @Value("${invoke.deliveryPoint.info}")
    private String deliveryPointInfoUrl;

    @Value("${invoke.hotels.list}")
    private String hotellistUrl;

    /**
     * 条件查询酒店列表
     *
     * @param accessToken
     * @param hotelVO
     * @return
     * @throws BusinessException
     */
    @Override
    public PageMo list(String accessToken, HotelVO hotelVO) throws BusinessException {
        PageMo listPageMo = new PageMo();
        String url = linenUrl + hotelsUrl;
        String js = JSONArray.toJSONString(hotelVO);
        JSONObject jsonParam = JSONArray.parseObject(js);
        jsonParam.put("isPack", 1); //配货app 只支持整扎的酒店
        jsonParam.put("factoryIds", hotelVO.getFactoryIds());
        String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
        JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObj.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObj.getString("retInfo"));
        }
        JSONObject dataJson = jsonObj.getJSONObject("page");
        if (dataJson == null) {
            return listPageMo;
        }
        Integer total = dataJson.getInteger("total");
        String list = dataJson.getString("list");
        List<HotelBo> hotelBos = JSONObject.parseArray(list, HotelBo.class);
        PageVO pageVO = new PageVO();
        pageVO.setPageNum(hotelVO.getPageNum());
        pageVO.setPageSize(hotelVO.getPageSize());
        listPageMo = new PageMo<>(hotelBos, pageVO, total);
        return listPageMo;
    }

    /**
     * 酒店详情所有信息
     *
     * @param token
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public HotelM findById(String token, String id) throws BusinessException {
        if (id == null) {
            return null;
        }
        String url = linenUrl + hotelInfoUrl;
        JSONObject param = new JSONObject();
        param.put("id", id);
        String httpEntitys = HttpUtil.doPost(url, token, param);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        String hotel = jsonObject1.getString("hotel");
        if (StringUtils.isBlank(hotel)) {
            return null;
        }
        HotelM hotelM = JSONObject.parseObject(hotel, HotelM.class);
        return hotelM;
    }


    /**
     * 酒店下的配送点
     *
     * @param token
     * @param hotelId
     * @return
     */
    @Override
    public List<DeliveryPointM> listpoints(String token, String hotelId) throws BusinessException {
        if (hotelId == null) {
            return new ArrayList<>();
        }
        String url = linenUrl + deliveryPointsUrl;
        JSONObject param = new JSONObject();
        param.put("hotelId", hotelId);
        param.put("pageNum", 1);
        param.put("pageSize", 60);
        String httpEntitys = HttpUtil.doPost(url, token, param);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        JSONObject page = jsonObject1.getJSONObject("page");
        if (page == null) {
            return new ArrayList<>();
        }
        List<DeliveryPointM> mList = JSONArray.parseArray(page.getString("list"), DeliveryPointM.class);
        if (mList != null && mList.size() > 0) {
            String orurl = cloudUrl + "/cloud/order/order/orderpoint";
            String data = InvokeUtil.invokeString(orurl, token, param);
            List<String> points = JSONObject.parseArray(data, String.class);
            for (int i = 0; i < mList.size(); i++) {
                DeliveryPointM point = mList.get(i);
                if (points.contains(point.getId())) {
                    point.setStatus(1);
                } else {
                    point.setStatus(0);
                }
            }
        }
        return mList;
    }

    /**
     * 酒店的简单信息
     *
     * @param token
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public HotelBo simple(String token, String id) throws BusinessException {
        if (id == null) {
            return null;
        }
        String url = linenUrl + hotelInfoUrl;
        JSONObject param = new JSONObject();
        param.put("id", id);
        String httpEntitys = HttpUtil.doPost(url, token, param);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        String hotel1 = jsonObject1.getString("hotel");
        if (StringUtils.isBlank(hotel1)) {
            return null;
        }
        HotelBo hotel = JSONObject.parseObject(hotel1, HotelBo.class);
        return hotel;

    }

    /**
     * 根据酒店ID查询酒店，返回map对象，key是酒店ID，value是酒店本身
     *
     * @param ids
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, HotelBo> findHotelsByIds(Set<String> ids) throws BusinessException {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }
        String url = cloudUrl + hotellistUrl;
        JSONObject param = new JSONObject();
        param.put("ids", ids);
        String httpEntitys = HttpUtil.doPost(url, "", param);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        List<HotelBo> hotelBos = JSONObject.parseArray(jsonObject1.getString("data"), HotelBo.class);
        Map<String, HotelBo> map = new HashMap<>();
        for (int i = 0; i < hotelBos.size(); i++) {
            HotelBo hotelBo = hotelBos.get(i);
            map.put(hotelBo.getId(), hotelBo);
        }
        return map;
    }

    /**
     * 根据酒店ID查询酒店，返回map对象，key是酒店ID，value是酒店名称
     *
     * @param ids
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, String> findHotelName(Set<String> ids) throws BusinessException {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }
        String url = cloudUrl + hotellistUrl;
        JSONObject param = new JSONObject();
        param.put("ids", ids);
        String httpEntitys = HttpUtil.doPost(url, "", param);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!"00000".equals(retCode)) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        List<HotelBo> hotelBos = JSONObject.parseArray(jsonObject1.getString("data"), HotelBo.class);
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < hotelBos.size(); i++) {
            HotelBo hotelBo = hotelBos.get(i);
            map.put(hotelBo.getId(), hotelBo.getName());
        }
        return map;
    }
}
