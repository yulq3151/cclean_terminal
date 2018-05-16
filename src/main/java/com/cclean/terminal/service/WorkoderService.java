package com.cclean.terminal.service;

import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.WorkOrder;
import com.cclean.terminal.vo.IdVO;
import com.cclean.terminal.vo.OrderIdsVO;
import com.cclean.terminal.vo.OrderVO;

import java.util.List;

/**
 * Created by hubin on 2018/3/23.
 */
public interface WorkoderService {

    WorkOrder generate(String accessToken, OrderIdsVO orderIdsVO) throws BusinessException;


    WorkOrder info(String accessToken, IdVO idVO) throws BusinessException;

    List<WorkOrder> list(String token, OrderVO orderVO) throws BusinessException;
}
