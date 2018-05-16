package com.cclean.terminal.mobileController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yulq
 * @create 2018-04-12 18:01
 * @desc
 **/
public class BaseMController {

    public String getToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if(token == null){
            token = request.getHeader("accessToken");
        }
        return token;
    }
}
