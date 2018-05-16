package com.cclean.terminal.mobileService;

import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model2.DeliveryOrder;
import com.cclean.terminal.model2.Order;
import com.cclean.terminal.model2.PickorderOrder;
import com.cclean.terminal.vo.OrderIdsVO;
import com.cclean.terminal.vo.OrderVO;
import com.cclean.terminal.vo.ZPickVo;

/**
 * @author yulq
 * @create 2018-04-12 21:33
 * @desc
 **/
public interface OrderMService {

    PageMo orderlist(String token, OrderVO orderVO) throws BusinessException;

    Order orderinfo(String token, String id) throws BusinessException;

    PageMo pickorderlist(String token, OrderVO orderVO) throws BusinessException;

    PickorderOrder pickorderinfo(String token, String id) throws BusinessException;

    PickorderOrder createPickOrder(String token, OrderIdsVO orderIdsVO) throws BusinessException;

    DeliveryOrder createDeliveryOrder(String token, ZPickVo zPickVo) throws BusinessException;

    PageMo myDeliveryorderlist(String token, OrderVO orderVO) throws BusinessException;

    PageMo deliveryorderlist(String token, OrderVO orderVO) throws BusinessException;

    DeliveryOrder deliveryOrderinfo(String token, String id) throws BusinessException;
}
