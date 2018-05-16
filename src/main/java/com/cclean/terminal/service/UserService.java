package com.cclean.terminal.service;

import com.cclean.terminal.config.Result;
import com.cclean.terminal.exception.BusinessException;
import com.cclean.terminal.model2.UserInfo;
import com.cclean.terminal.vo.LoginVO;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

public interface UserService {

    Result login(LoginVO loginVO, HttpServletResponse response) throws BusinessException;

    void logout(String token, HttpServletResponse response) throws BusinessException;


    UserInfo mobilelogin(LoginVO loginVO) throws BusinessException;

    Map<String, String> findUsersByIds(Set<String> ids, String token) throws BusinessException;
}
