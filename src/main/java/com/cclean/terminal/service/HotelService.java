package com.cclean.terminal.service;

import com.cclean.terminal.config.Result;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.DeliveryPoint;
import com.cclean.terminal.model.Hotel;
import com.cclean.terminal.vo.HotelVO;
import com.cclean.terminal.vo.IdVO;
import com.cclean.terminal.vo.PointVO;

import java.util.Map;
import java.util.Set;

/**
 * Created by hubin on 2018/3/23.
 */
public interface HotelService {

    Result hotels(String accessToken, HotelVO hotelVO) throws BusinessException;

    Result deliverypoints(String accessToken, PointVO pointVO) throws BusinessException;

    Hotel findHotelById(String accessToken, IdVO idVO) throws BusinessException;

    DeliveryPoint findPointById(String accessToken, IdVO idVO) throws BusinessException;

    Map<String, DeliveryPoint> findPointsByIds(Set<String> ids) throws BusinessException;

    Map<String, Hotel> findHotelsByIds(Set<String> ids) throws BusinessException;
}
