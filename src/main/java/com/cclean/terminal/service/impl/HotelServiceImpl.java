package com.cclean.terminal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.config.Result;
import com.cclean.terminal.constant.Constant;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.DeliveryPoint;
import com.cclean.terminal.model.Hotel;
import com.cclean.terminal.service.HotelService;
import com.cclean.terminal.util.HttpUtil;
import com.cclean.terminal.util.InvokeUtil;
import com.cclean.terminal.util.PowerUtils;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.vo.HotelVO;
import com.cclean.terminal.vo.IdVO;
import com.cclean.terminal.vo.PageVO;
import com.cclean.terminal.vo.PointVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by hubin on 2018/3/23.
 * 酒店相关逻辑
 */
@Service
public class HotelServiceImpl implements HotelService {

    private static Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);

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

    @Value("${invoke.points.list}")
    private String pointslisturl;

    @Autowired
    PowerUtils powerUtils;

    /**
     * 查询 酒店列表
     *
     * @param hotelVO
     * @return
     * @throws BusinessException 酒店列表
     */
    @Override
    public Result hotels(String accessToken, HotelVO hotelVO) throws BusinessException {
        Result result = Result.success();
        try {
            if (hotelVO.getPageNum() == null && hotelVO.getPageSize() == null) {
                result.setCodeInfo("00100", "分页参数必填");
            }
            if (accessToken == null) return Result.paramNull();
            logger.info("accessToken value is:{}", accessToken);
            List<String> factoryIds = powerUtils.getFactoryIds(accessToken);
            if (factoryIds == null || factoryIds.size() <= 0) {
                result.setCodeInfo(Constant.RET_CODE_ERROR, "权限不足");
                return result;
            }
            List<Hotel> hotelList = new ArrayList<>();
            String url = linenUrl + hotelsUrl;
            hotelVO.setFactoryIds(factoryIds);
            String js = JSONArray.toJSONString(hotelVO);
            JSONObject jsonParam = JSONArray.parseObject(js);
            String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
            logger.info("酒店列表:{}",httpEntitys);
            JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                result.setCodeInfo(retCode, retInfo);
                return result;
            }

            JSONObject dataJson = jsonObj.getJSONObject("page");
            if (dataJson == null) return Result.objNull();
            JSONArray jsonArray = dataJson.getJSONArray("list");
            if (jsonArray == null) return Result.objNull();

            for (int i = 0, j = jsonArray.size(); i < j; i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                if (json == null) return Result.objNull();
                Hotel hotel = new Hotel();
                hotel.setId(json.getString("id"));
                hotel.setName(json.getString("name"));
                hotel.setAddress(json.getString("address"));
                hotel.setIsTotal(json.getInteger("isTotal"));
                hotelList.add(hotel);
            }

            PageMo<Hotel> page = new PageMo<Hotel>(hotelList,
                    new PageVO(hotelVO.getPageNum(), hotelVO.getPageSize()),
                    dataJson.getInteger("total"));
            result.setData(page);
            return result;
        } catch (Exception e) {
            logger.error(Constant.RET_CODE_DEBUG, e);
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getMessage());
            return result;
        }
    }

    /**
     * 酒店下的配送点
     *
     * @param accessToken
     * @param pointVO
     * @return
     * @throws BusinessException
     */
    @Override
    public Result deliverypoints(String accessToken, PointVO pointVO) throws BusinessException {
        Result result = Result.success();
        try {
            if (accessToken == null) return Result.paramNull();
            logger.info("accessToken value is:{}", accessToken);
            if (pointVO.getPageNum() == null || pointVO.getPageSize() == null) {
                result.setCodeInfo("00001", "分页参数必填");
                return result;
            }
            if (pointVO.getHotelId() == null) {
                result.setCodeInfo("00001","酒店ID不能为空");
                return result;
            }
            List<DeliveryPoint> deliveryPointList = new ArrayList<>();
            String url = linenUrl + "/cloud/manage/v1/deliveryPoint/page";
            logger.info("deliveryPoints cloudUrl is:{}" , url);
            String js = JSONArray.toJSONString(pointVO);
            JSONObject jsonParam = JSONArray.parseObject(js);
            String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
            logger.info("酒店下的配送点:{}",httpEntitys);
            JSONObject jsonObj = JSONObject.parseObject(httpEntitys);
            String retCode = jsonObj.getString("retCode");
            if (!retCode.equals(Constant.RET_CODE_SUCCESS)) {
                String retInfo = jsonObj.getString("retInfo");
                result.setCodeInfo(retCode, retInfo);
                return result;
            }
            JSONObject dataJson = jsonObj.getJSONObject("page");
            JSONArray jsonArray = dataJson.getJSONArray("list");
            if (jsonArray == null || jsonArray.size() == 0) return Result.objNull();
            List<String> pointIds = new ArrayList<>();
            for (int i = 0, j = jsonArray.size(); i < j; i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                DeliveryPoint deliveryPoint = new DeliveryPoint();
                String id = json.getString("id");
                pointIds.add(id);
                deliveryPoint.setId(id);
                deliveryPoint.setFloor(json.getString("name"));
                deliveryPointList.add(deliveryPoint);
            }
            //查询配送点有无订单
            if (pointIds.size()>0) {
                String orurl= cloudUrl+"/cloud/order/order/orderpoint";
                String data = InvokeUtil.invokeString(orurl, accessToken, jsonParam);
                List<String> list = JSONObject.parseArray(data, String.class);
                for (int i = 0; i < deliveryPointList.size(); i++) {
                    DeliveryPoint point = deliveryPointList.get(i);
                    if (list.contains(point.getId())) {
                        point.setStatus(1);
                    }else {
                        point.setStatus(0);
                    }
                }
            }

            PageMo<DeliveryPoint> page = new PageMo<>(deliveryPointList,
                    new PageVO(pointVO.getPageNum(), pointVO.getPageSize()),
                    dataJson.getInteger("total"));

            result.setData(page);
            return result;
        } catch (Exception e) {
            logger.error(Constant.RET_CODE_DEBUG, e);
            result.setCodeInfo(Constant.RET_CODE_DEBUG, e.getMessage());
            return result;
        }
    }

    /**
     * 根据酒店ID查询详情
     *
     * @param accessToken 授权码
     * @param idVO        参数(ID)
     * @return
     * @throws BusinessException
     */
    @Override
    public Hotel findHotelById(String accessToken, IdVO idVO) throws BusinessException {
        Hotel hotel = new Hotel();
        if (StringUtils.isHasEmpty(idVO.getId())) {
            return null;
        }
        String url = linenUrl + hotelInfoUrl;
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("id", idVO.getId());
        String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
        logger.info("酒店详情:{}",httpEntitys);
        JSONObject jsonResult = JSONObject.parseObject(httpEntitys);
        String retCode = jsonResult.getString("retCode");
        if (StringUtils.isNotEquals("00000", retCode)) {
            throw new BusinessException(retCode, jsonResult.getString("retInfo"));
        }
        JSONObject json = jsonResult.getJSONObject("hotel");
        if (json == null) {
            return hotel;
        }
        hotel.setId(json.getString("id"));
        hotel.setName(json.getString("name"));
        hotel.setAddress(json.getString("address"));
        hotel.setIsTotal(json.getInteger("isTotal"));
        return hotel;
    }

    /**
     * 根据配送点ID查询详情
     *
     * @param accessToken 授权码
     * @param idVO        参数(ID)
     * @return
     * @throws BusinessException
     */
    @Override
    public DeliveryPoint findPointById(String accessToken, IdVO idVO) throws BusinessException {
        DeliveryPoint deliveryPoint = new DeliveryPoint();
        if (StringUtils.isHasEmpty(idVO.getId())) {
            return null;
        }
        String url = linenUrl + deliveryPointInfoUrl;
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("id", idVO.getId());
        String httpEntitys = HttpUtil.doPost(url, accessToken, jsonParam);
        logger.info("配送点详情:{}",httpEntitys);
        JSONObject jsonResult = JSONObject.parseObject(httpEntitys);
        String retCode = jsonResult.getString("retCode");
        if (StringUtils.isNotEquals("00000", retCode)) {
            throw new BusinessException(retCode, jsonResult.getString("retInfo"));
        }
        JSONObject dataJson = jsonResult.getJSONObject("deliveryPoint");
        if (dataJson == null) {
            return null;
        }
        deliveryPoint.setId(dataJson.getString("id"));
        deliveryPoint.setFloor(dataJson.getString("name"));
        return deliveryPoint;
    }

    /**
     * 根据配送点ID查询，返回map对象，key是配送点ID，value是配送点本身
     *
     * @param ids
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, DeliveryPoint> findPointsByIds(Set<String> ids) throws BusinessException {
        String url = cloudUrl + pointslisturl;
        JSONObject param = new JSONObject();
        param.put("ids", ids);
        String httpEntitys = HttpUtil.doPost(url, "", param);
        logger.info("配送点列表:{}",httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!retCode.equals("00000")) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        JSONArray array = jsonObject1.getJSONArray("data");
        Map<String, DeliveryPoint> map = new HashMap<>();
        if (array != null && array.size() > 0) {
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = array.getJSONObject(i);
                DeliveryPoint dp = new DeliveryPoint();
                String id = obj.getString("id");
                dp.setId(id);
                dp.setFloor(obj.getString("name"));
                map.put(id, dp);
            }
        }
        return map;
    }

    /**
     * 批量-根据酒店ID查询酒店，返回map对象，key是酒店ID，value是酒店本身
     *
     * @param ids
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, Hotel> findHotelsByIds(Set<String> ids) throws BusinessException {
        String url = cloudUrl + hotellistUrl;
        JSONObject param = new JSONObject();
        param.put("ids", ids);
        String httpEntitys = HttpUtil.doPost(url, "", param);
        logger.info("酒店列表:{}",httpEntitys);
        JSONObject jsonObject1 = JSONObject.parseObject(httpEntitys);
        String retCode = jsonObject1.getString("retCode");
        if (!retCode.equals("00000")) {
            throw new BusinessException(retCode, jsonObject1.getString("retInfo"));
        }
        List<Hotel> hotelBos = JSONObject.parseArray(jsonObject1.getString("data"), Hotel.class);
        Map<String, Hotel> map = new HashMap<>();
        for (int i = 0; i < hotelBos.size(); i++) {
            Hotel hotelBo = hotelBos.get(i);
            map.put(hotelBo.getId(), hotelBo);
        }
        return map;
    }

}
