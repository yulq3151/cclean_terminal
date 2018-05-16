package com.cclean.terminal.mobileService;

import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model2.DeliveryPointM;
import com.cclean.terminal.model2.HotelBo;
import com.cclean.terminal.model2.HotelM;
import com.cclean.terminal.vo.HotelVO;
import com.cclean.terminal.vo.IdVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yulq
 * @create 2018-04-12 18:04
 * @desc
 **/
public interface HotelMService {
    PageMo list(String accessToken, HotelVO hotelVO) throws BusinessException;

    HotelM getOne(String token, String id) throws BusinessException;

    List<DeliveryPointM> listpoints(String token, String hotelId) throws BusinessException;

    HotelBo simple(String token, String id) throws BusinessException;

    Map<String,HotelBo> findHotelsByIds(Set<String> ids) throws BusinessException;
}
