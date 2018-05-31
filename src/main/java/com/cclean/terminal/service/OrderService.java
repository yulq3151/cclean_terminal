package com.cclean.terminal.service;

import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.Order;
import com.cclean.terminal.vo.IdVO;
import com.cclean.terminal.vo.OrderVO;

import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 */
public interface OrderService {

    Order info(String accessToken, IdVO idVO) throws BusinessException;

    List<Order> list(String token, OrderVO orderVO) throws BusinessException;

    List<Order> dirList(String token, int pageNum, int pageSize, List<String> hotelIds, List<String> pointIds, String startTime, String endTime, int checkstate) throws BusinessException;
}
