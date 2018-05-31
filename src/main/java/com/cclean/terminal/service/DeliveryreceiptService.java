package com.cclean.terminal.service;

import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.DeliveryReceipt;
import com.cclean.terminal.vo.GenerateVO;
import com.cclean.terminal.vo.IdVO;
import com.cclean.terminal.vo.OrderVO;

import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 */
public interface DeliveryreceiptService {

    DeliveryReceipt generate(String token, GenerateVO generateVO) throws BusinessException;

    DeliveryReceipt info(String accessToken, IdVO idVO) throws BusinessException;

    List<DeliveryReceipt> list(String token, OrderVO orderVO) throws BusinessException;
}
