package com.cclean.terminal.controller;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    public String getToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if(token == null){
            token = request.getHeader("accessToken");
        }
        return token;
    }

}
