package com.cclean.terminal.mobileService;

import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.Sku;
import com.cclean.terminal.model2.DeliveryPointM;
import com.cclean.terminal.model2.LinenPackM;
import com.cclean.terminal.model2.UserInfo;
import com.cclean.terminal.model2.VersionInfo;
import com.cclean.terminal.vo.PageVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yulq
 * @create 2018-04-12 20:08
 * @desc
 **/
public interface ConService {

    PageMo lines(PageVO pageVO, String token, String state, String name, String id) throws BusinessException;

    PageMo brands(PageVO pageVO, String token, String name) throws BusinessException;

    LinenPackM pack(String token, String id) throws BusinessException;

    boolean updatepack(String token, List<String> ids, String type) throws BusinessException;

    DeliveryPointM deliveryPoint(String token, String id) throws BusinessException;


    Map<String,DeliveryPointM> findPointsByIds(Set<String> ids) throws BusinessException;

    VersionInfo versionUpdate(String param, String token) throws BusinessException;

    Map<String,String> findUsersByIds(Set<String> ids, String token) throws BusinessException;

    Map<String,Sku> findSkusByIds(Set<String> ids) throws BusinessException;

    PageMo<UserInfo> findUsersByType(String token, int type, int modelType, int pageNum, int pageSize) throws BusinessException;
}
