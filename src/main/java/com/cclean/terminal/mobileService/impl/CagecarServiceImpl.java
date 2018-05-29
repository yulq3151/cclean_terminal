package com.cclean.terminal.mobileService.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.mobileService.CagecarService;
import com.cclean.terminal.model.Sku;
import com.cclean.terminal.model2.Cagecar;
import com.cclean.terminal.model2.CagecarUseLog;
import com.cclean.terminal.model2.LinenPackM;
import com.cclean.terminal.service.SkuService;
import com.cclean.terminal.util.InvokeUtil;
import com.cclean.terminal.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author yulq
 * @create 2018-05-23 11:38
 * @desc 笼车
 **/
@Service
public class CagecarServiceImpl implements CagecarService {


    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    @Resource
    private SkuService skuService;

    /**
     * 笼车登记
     *
     * @param token
     * @param codes
     * @param factoryId
     * @return 登记成功的笼车ids
     */
    @Override
    public boolean register(String token, List<String> codes, String factoryId) throws BusinessException {
        String url = cloudUrl + "/cagecar/api/cagecar/insert";
        JSONObject param = new JSONObject();
        param.put("codes", codes);
        param.put("factoryId", factoryId);
        InvokeUtil.invokeString(url, token, param);
        return true;
    }

    /**
     * 笼车查询
     *
     * @param token
     * @param codes
     * @return
     */
    @Override
    public List<Cagecar> list(String token, List<String> codes) throws BusinessException {
        if (codes == null || codes.size() == 0) {
            return new ArrayList<>();
        }
        String url = cloudUrl + "/cagecar/api/cagecar/list";
        JSONObject param = new JSONObject();
        param.put("codes", new HashSet<>(codes));
        String data = InvokeUtil.invokeString(url, token, param);
        List<Cagecar> cagecars = JSONArray.parseArray(data, Cagecar.class);
        return cagecars;
    }

    /**
     * 笼车的使用
     *
     * @param token     借用人
     * @param status    状态
     * @param userId    使用人
     * @param factoryId 使用工厂
     * @param codes     笼车code
     * @param packCodes
     * @return 使用记录的ids
     */
    @Override
    public boolean cagecarUse(String token, int status, String userId, String factoryId, List<String> codes, List<String> packCodes) throws BusinessException {
        switch (status) {
            case 10:
                this.borrow(token, userId, factoryId, status, codes);
                return true;
            case 20:
                this.signEmpty(token, status, codes);
                return true;
            case 30:
                this.load(token, codes, packCodes,status );
                return true;
            case 40:
                this.back(token, userId, status, codes);
                return true;
            case 50:
                this.signSome(token, status, codes);
                return true;
            default:
                throw new BusinessException("00001", "输入状态有误");
        }
    }

    /**
     * 查询笼车当前使用记录
     *
     * @param token
     * @param code  笼车
     * @return 最新的一条记录
     */
    @Override
    public CagecarUseLog recored(String token, String code) throws BusinessException {
        if (code == null) {
            return null;
        }
        String url = cloudUrl + "/cagecar/api/cagecar/getcurrentstate";
        JSONObject param = new JSONObject();
        param.put("cagecarCode", code);
        String data = InvokeUtil.invokeString(url, token, param);
        CagecarUseLog useLog = JSONObject.parseObject(data, CagecarUseLog.class);
        return useLog;
    }


    /**
     * 查询笼车+货
     *
     * @param token
     * @param code  使用记录IDk
     * @return 打扎单详情
     */
    @Override
    public List<LinenPackM> findPacks(String token, String code) throws BusinessException {
        if (StringUtils.isBlank(code)) {
            throw new BusinessException("00001", "笼车code有误");
        }
        List<LinenPackM> list = new ArrayList<>();
        String url = cloudUrl + "/cagecar/api/delivery/item/list";
        JSONObject param = new JSONObject();
        param.put("cagecarCode", code);
        String data = InvokeUtil.invokeString(url, token, param);
        List<JSONObject> array = JSONArray.parseArray(data, JSONObject.class);
        if (array == null || array.isEmpty()) {
            return list;
        }
        List<String> kzids = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.get(i);
            String packCode = object.getString("packCode");
            kzids.add(packCode);
            LinenPackM packM = new LinenPackM();
            packM.setCode(packCode);
            list.add(packM);
        }
        //查询打扎单详情
        url = cloudUrl + "/linen/api/linen/pack/list";
        param.clear();
        param.put("ids", kzids);
        String datakz = InvokeUtil.invokeString(url, token, param);
        List<JSONObject> kzdata = JSONArray.parseArray(datakz, JSONObject.class);
        if (kzdata != null && kzdata.size() > 0) {
            Map<String, String> skumap = new HashMap<>();
            Map<String, Integer> countmap = new HashMap<>();
            Set<String> skuids = new HashSet<>();
            for (int i = 0; i < kzdata.size(); i++) {
                JSONObject pack = kzdata.get(i);
                String packcode = pack.getString("code");
                String skuId = pack.getString("skuId");
                int count = pack.getIntValue("count");
                skuids.add(skuId);
                skumap.put(packcode, skuId);
                countmap.put(packcode, count);
            }
            //查询Sku详情
            Map<String, Sku> skus = this.skuService.findSkusByIds(skuids);
            for (int i = 0; i < list.size(); i++) {
                LinenPackM packM = list.get(i);
                String packcode = packM.getCode();
                String skuId = skumap.get(packcode);
                packM.setSkuId(skuId);
                packM.setSkuName(skus.get(skuId) == null ? null : skus.get(skuId).getName());
                packM.setCount(countmap.get(packcode));
            }
        }
        return list;
    }


    /**
     * 笼车领用
     *
     * @param token     借出人
     * @param userId    借用人
     * @param factoryId 送达工厂
     * @return
     */
    private boolean borrow(String token, String userId, String factoryId, int status, List<String> codes) throws BusinessException {
        if (StringUtils.isHasEmpty(token, userId, factoryId)) {
            throw new BusinessException("00001", "参数不足");
        }
        String url = cloudUrl + "/cagecar/api/cagecar/transform";
        JSONObject param = new JSONObject();
        param.put("status", status);
        param.put("cagecarCodes", codes);
        param.put("borrowDriverId", userId);
        param.put("factoryId", factoryId);
        InvokeUtil.invokeString(url, token, param);
        return true;
    }

    /**
     * 空笼车签收
     *
     * @param token 签收人
     * @return
     */
    private boolean signEmpty(String token, int status, List<String> codes) throws BusinessException {
        String url = cloudUrl + "/cagecar/api/cagecar/transform";
        JSONObject param = new JSONObject();
        param.put("status", status);
        param.put("cagecarCodes", codes);
        InvokeUtil.invokeString(url, token, param);
        return true;
    }

    /**
     * 笼车配货
     *
     * @param token
     * @param codes   使用记录的id
     * @param kzcodes 打扎单号
     * @param status
     * @return 使用记录ID
     */
    private boolean load(String token, List<String> codes, List<String> kzcodes, int status) throws BusinessException {
        if (codes == null || codes.isEmpty() || codes.size() > 1) {
            throw new BusinessException("00001", "笼车code有误");
        }
        String url = cloudUrl + "/cagecar/api/delivery/item/insert";
        JSONObject param = new JSONObject();
        param.put("cagecarCodes", codes);
        param.put("status", status);
        param.put("packCodes", new HashSet<>(kzcodes));
        InvokeUtil.invokeString(url, token, param);
        return true;
    }

    /**
     * 笼车+货 出厂
     *
     * @param token
     * @return
     */
    private boolean back(String token, String userId, int status, List<String> codes) throws BusinessException {
        if (StringUtils.isHasEmpty(token, userId)) {
            throw new BusinessException("00001", "参数不足");
        }
        String url = cloudUrl + "/cagecar/api/cagecar/transform";
        JSONObject param = new JSONObject();
        param.put("status", status);
        param.put("cagecarCodes", codes);
        param.put("deliveryDriverId", userId);
        InvokeUtil.invokeString(url, token, param);
        return true;
    }

    /**
     * 笼车+货 签收
     *
     * @param token
     * @return
     */
    private boolean signSome(String token, int status, List<String> codes) throws BusinessException {
        String url = cloudUrl + "/cagecar/api/cagecar/transform";
        JSONObject param = new JSONObject();
        param.put("status", status);
        param.put("cagecarCodes", codes);
        InvokeUtil.invokeString(url, token, param);
        return true;
    }


    /**
     * 使用记录条件查询
     *
     * @param token
     * @param pageNum
     * @param pageSize
     * @param statusArr    状态可多选择
     * @param cagecarCode  笼车code
     * @param useTimeStart 借用时间  开始
     * @param useTimeEnd   借用时间  结尾
     * @param type
     * @return
     */
    @Override
    public PageMo<CagecarUseLog> query(String token, int pageNum, int pageSize, List<Integer> statusArr, String cagecarCode, String useTimeStart, String useTimeEnd, String type) throws BusinessException {
        String url = cloudUrl + "/cagecar/api/cagecar/uselog/page";
        JSONObject param = new JSONObject();
        if (pageNum <= 0 || pageSize < 0) {
            throw new BusinessException("00001", "分页参数有误");
        }
        param.put("pageNum",pageNum);
        param.put("pageSize",pageSize);
        if (statusArr != null && statusArr.size() > 0) {
            param.put("statusArr", statusArr);
        }
        if (!StringUtils.isBlank(cagecarCode)) {
            param.put("cagecarCode", cagecarCode);
        }
        if (!StringUtils.isBlank(useTimeStart)) {
            param.put("useTimeStart", useTimeStart);
        }
        if (!StringUtils.isBlank(useTimeEnd)) {
            param.put("useTimeEnd", useTimeEnd);
        }
        if (!StringUtils.isBlank(type)) {
            param.put("type", type);
        }
        JSONObject data = InvokeUtil.invokeResult(url, token, param);
        int total = data.getIntValue("total");
        String list = data.getString("list");
        List<CagecarUseLog> logList = JSONArray.parseArray(list, CagecarUseLog.class);
        return new PageMo<>(logList, pageNum, pageSize, total);
    }


}
