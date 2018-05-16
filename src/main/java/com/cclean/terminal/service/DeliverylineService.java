package com.cclean.terminal.service;

import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.Deliveryline;
import com.cclean.terminal.vo.PageVO;

/**
 * @author yulq
 * @create 2018-04-11 15:57
 * @desc
 **/
public interface DeliverylineService {

    Deliveryline add(String name);

    Deliveryline update(String id);

    Deliveryline delete(String id);

    PageMo getdeliverylines(String token, PageVO pageVO) throws BusinessException;
}
