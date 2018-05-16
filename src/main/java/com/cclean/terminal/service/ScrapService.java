package com.cclean.terminal.service;

import com.cclean.terminal.config.Result;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model.ScrapReason;
import com.cclean.terminal.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * Created by hubin on 2018/3/23.
 */
public interface ScrapService {

    Result scrapreason(String accessToken, PageVO pageVO);
}
