package com.cclean.terminal.mobileService.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.mobileService.HotelMService;
import com.cclean.terminal.mobileService.LinenPackageService;
import com.cclean.terminal.model2.*;
import com.cclean.terminal.util.InvokeUtil;
import com.cclean.terminal.util.StringUtils;
import com.cclean.terminal.vo.LinenPackageVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author yulq
 * @create 2018-05-11 9:33
 * @desc
 **/
@Service
public class LinenPackageServiceImpl implements LinenPackageService {

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Resource
    private HotelMService hotelMService;

    @Resource
    private ConServiceImpl conService;

    /**
     * 布草袋登记
     *
     * @param token
     * @param codes
     * @param type
     * @param color
     * @param batch
     * @return
     */
    @Override
    public List<String> register(String token, List<String> codes, int type, String color, String batch) throws BusinessException {
        Set<String> set = new HashSet<>(codes);
        String url = cloudUrl + "/linen/api/package/insert";
        JSONObject param = new JSONObject();
        param.put("codes", set);
        param.put("color", color);
        param.put("linentype", type);
        param.put("batch", batch);
        String json = InvokeUtil.invokeString(url, token, param);
        List<String> list = JSONObject.parseArray(json, String.class);
        return list;

    }

    /**
     * 修改布草袋的基础信息
     *
     * @param token
     * @param packge
     * @return
     */
    @Override
    public boolean update(String token, LinenPackageVO packge) throws BusinessException {
        String url = cloudUrl + "/linen/api/package/update";
        JSONObject param = new JSONObject();
        param.put("id", packge.getId());
        param.put("color", packge.getColor());
        param.put("linentype", packge.getLinentype());
        param.put("linenstate", packge.getLinenstate());
        param.put("batch", packge.getBatch());
        param.put("washNum", packge.getWashNum());
        InvokeUtil.invokeString(url, token, param);
        return true;
    }

    /**
     * 修改布草袋流转状态
     *
     * @param token
     * @param codes
     * @param linentype 类型
     * @param usetype   状态
     * @param userId    使用人
     * @param hotelId   酒店ID
     * @param pointId   配送点ID
     * @return 修改成功
     */
    @Override
    public boolean circulate(String token, List<String> codes, int linentype, int usetype, String userId, String hotelId, String pointId) throws BusinessException {
        String url = cloudUrl + "/linen/api/package/update";
        JSONObject param = new JSONObject();
        param.put("codes", new HashSet<>(codes));
        param.put("usetype", usetype);
        param.put("linentype", linentype);
        param.put("hotelId", hotelId);
        param.put("pointId", pointId);
        param.put("userId", userId);
        InvokeUtil.invokeString(url, token, param);
        return true;
    }

    /**
     * 布草袋分页查询
     *
     * @param token
     * @param packageVO
     * @return
     */
    @Override
    public PageMo<LinenPackage> pagePackage(String token, LinenPackageVO packageVO) throws BusinessException {
        String url = cloudUrl + "/linen/api/package/page";
        JSONObject param = JSON.parseObject(JSONObject.toJSONString(packageVO));
        JSONObject data = InvokeUtil.invokeResult(url, token, param);
        if (data == null) {
            return new PageMo<>();
        }
        String listjson = data.getString("list");
        int total = data.getIntValue("total");
        List<LinenPackage> list = JSONObject.parseArray(listjson, LinenPackage.class);
        if (list == null || list.size() == 0) {
            return new PageMo<>();
        }
        return new PageMo<>(list, packageVO.getPageNum(), packageVO.getPageSize(), total);
    }

    /**
     * 根据codes批量查询布草袋
     *
     * @param token
     * @param codes
     * @return
     */
    @Override
    public List<LinenPackage> listPackage(String token, List<String> codes) throws BusinessException {
        List<LinenPackage> list = new ArrayList<>();
        if (codes == null || codes.size() == 0) {
            return list;
        }
        String url = cloudUrl + "/linen/api/package/list";
        JSONObject param = new JSONObject();
        param.put("codes", new HashSet<>(codes));
        String data = InvokeUtil.invokeString(url, token, param);
        list = JSONObject.parseArray(data, LinenPackage.class);
        return list;
    }

    /**
     * 查询布草袋的使用记录
     *
     * @param token
     * @param code
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageMo<LinenPackageRecord> record(String token, String code, int pageNum, int pageSize) throws BusinessException {
        String url = cloudUrl + "/linen/api/package/detail/page";
        JSONObject param = new JSONObject();
        param.put("code", code);
        param.put("pageNum", pageNum);
        param.put("pageSize", pageSize);
        JSONObject data = InvokeUtil.invokeResult(url, token, param);
        if (data == null) {
            return new PageMo<>();
        }
        String listjson = data.getString("list");
        int total = data.getIntValue("total");
        List<LinenPackageRecord> list = JSONObject.parseArray(listjson, LinenPackageRecord.class);
        if (list == null || list.size() == 0) {
            return new PageMo<>();
        }
        Set<String> hotelIds = new HashSet<>();
        Set<String> pointIds = new HashSet<>();
        Set<String> userIds = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            LinenPackageRecord record = list.get(i);
            hotelIds.add(record.getHotelId());
            pointIds.add(record.getPointId());
            userIds.add(record.getCreator());
        }
        Map<String, String> hotels = this.hotelMService.findHotelName(hotelIds);
        Map<String, String> points = this.conService.findPointName(pointIds);
        Map<String, String> users = this.conService.findUsersByIds(userIds, token);
        for (int i = 0; i < list.size(); i++) {
            LinenPackageRecord record = list.get(i);
            String hotelId = record.getHotelId();
            String pointId = record.getPointId();
            String userId = record.getCreator();
            record.setHotelName(hotels.get(hotelId));
            if (hotelId.equals(pointId)) {
                record.setPointName("总仓");
            } else {
                record.setPointName(points.get(pointId));
            }
            record.setCreatorName(users.get(userId));
        }
        return new PageMo<>(list, pageNum, pageSize, total);
    }

    /**
     * 查询净布草袋的报表
     *
     * @param token
     * @param userId   使用人
     * @param beginNum 最早天数
     * @param endNum   最晚天数
     * @return
     */
    @Override
    public List<LinenPackageStatistics> fineReport(String token, String userId, int beginNum, int endNum) throws BusinessException {
        String url = cloudUrl + "/linen/api/package/clean/report2";
        JSONObject param = new JSONObject();
        param.put("userId", userId);
        param.put("beginNum", beginNum);
        param.put("endNum", endNum);
        String datajson = InvokeUtil.invokeString(url, token, param);
        List<LinenPackageStatistics> list = JSONArray.parseArray(datajson, LinenPackageStatistics.class);
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        Set<String> hotelIds = new HashSet<>();
        Set<String> pointIds = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            LinenPackageStatistics statistics = list.get(i);
            String hotelId = statistics.getHotelId();
            hotelIds.add(hotelId);
            List<LinenPackageStacount> stacounts = statistics.getHotelData();
            if (stacounts != null && stacounts.size() > 0) {
                for (int j = 0; j < stacounts.size(); j++) {
                    LinenPackageStacount stacount = stacounts.get(j);
                    String pointId = stacount.getPointId();
                    if (!hotelId.equals(pointId)) {
                        pointIds.add(pointId);
                    }
                }
            }
        }
        Map<String, String> hotels = this.hotelMService.findHotelName(hotelIds);
        Map<String, String> points = this.conService.findPointName(pointIds);
        for (int i = 0; i < list.size(); i++) {
            LinenPackageStatistics statistics = list.get(i);
            String hotelId = statistics.getHotelId();
            statistics.setHotelName(hotels.get(hotelId));
            List<LinenPackageStacount> stacounts = statistics.getHotelData();
            if (stacounts != null && stacounts.size() > 0) {
                for (int j = 0; j < stacounts.size(); j++) {
                    LinenPackageStacount stacount = stacounts.get(j);
                    String pointId = stacount.getPointId();
                    if (pointId != null) {
                        if (hotelId.equals(pointId)) {
                            stacount.setPointName("总仓");
                        } else {
                            stacount.setPointName(points.get(pointId));
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 查询脏布草袋的报表
     *
     * @param token
     * @param userId   使用人
     * @param beginNum 最早天数
     * @param endNum   最晚天数
     * @return
     */
    @Override
    public LinenPackageStacount dirtyReport(String token, String userId, int beginNum, int endNum) throws BusinessException {
        String url = cloudUrl + "/linen/api/package/dirty/report";
        JSONObject param = new JSONObject();
        param.put("userId", userId);
        param.put("beginNum", beginNum);
        param.put("endNum", endNum);
        String datajson = InvokeUtil.invokeString(url, token, param);
        LinenPackageStacount stacount = JSONObject.parseObject(datajson, LinenPackageStacount.class);
        return stacount;

    }

    /**
     * 脏布草袋打扎
     *
     * @param token
     * @param codes
     * @return
     */
    @Override
    public LinenPackageKZ packageKZ(String token, List<String> codes) throws BusinessException {
        if (codes == null || codes.isEmpty()) {
            return null;
        }
        String url = cloudUrl + "/cagecar/api/package/pack";
        JSONObject param = new JSONObject();
        param.put("codes", new HashSet<>(codes));
        String data = InvokeUtil.invokeString(url, token, param);
        LinenPackageKZ packageKZ = JSONObject.parseObject(data, LinenPackageKZ.class);
        return packageKZ;
    }

    /**
     * 查询打扎里的脏布草袋
     *
     * @param
     * @param token
     * @return
     */
    @Override
    public LinenPackageKZ findPackageKZ(String token, String packageKZ) throws BusinessException {
        if (StringUtils.isBlank(packageKZ)) {
            return null;
        }
        String url = cloudUrl + "/cagecar/api/package/info";
        JSONObject param = new JSONObject();
        param.put("id", packageKZ);
        String datakz = InvokeUtil.invokeString(url, token, param);
        LinenPackageKZ packKZ = JSONObject.parseObject(datakz, LinenPackageKZ.class);
        if (packKZ != null && packKZ.getId() != null) {
            url = cloudUrl + "/cagecar/api/package/itemList";
            param.put("linenPackagePackId", packageKZ);
            String data = InvokeUtil.invokeString(url, token, param);
            List<JSONObject> array = JSONArray.parseArray(data, JSONObject.class);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = array.get(i);
                String code = object.getString("packageCode");
                list.add(code);
            }
            packKZ.setCodes(list);
        }

        return packKZ;
    }

    /**
     * 打扎袋子领用
     *
     * @param token      操作人
     * @param userId     使用人
     * @param packageKZS
     * @return
     */
    @Override
    public boolean borrow(String token, String userId, List<String> packageKZS) throws BusinessException {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("00001", "请指明使用人");
        }
        String url = cloudUrl + "/cagecar/api/package/itemList";
        JSONObject param = new JSONObject();
        Set<String> set = new HashSet<>();
        for (int i = 0; i < packageKZS.size(); i++) {
            param.put("linenPackagePackId", packageKZS.get(i));
            String data = InvokeUtil.invokeString(url, token, param);
            List<JSONObject> array = JSONArray.parseArray(data, JSONObject.class);
            for (int j = 0; j < array.size(); j++) {
                JSONObject object = array.get(j);
                String code = object.getString("packageCode");
                set.add(code);
            }
        }
        url = cloudUrl + "/cagecar/api/package/batchUpdate";
        param.put("userId", userId);
        param.put("ids", packageKZS);
        param.put("codes",set);
        InvokeUtil.invokeString(url, token, param);
        return true;
    }

}
