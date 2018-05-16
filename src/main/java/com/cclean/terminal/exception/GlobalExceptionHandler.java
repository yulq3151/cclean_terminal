package com.cclean.terminal.exception;

import com.cclean.terminal.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Map defaultErrorHandler(HttpServletRequest req, BusinessException e) throws Exception {
        logger.error("************************异常BusinessException开始*******************************");
        logger.error(e.getLocalizedMessage(), e);
        logger.error("************************异常BusinessException结束*******************************");
        Map<String, Object> map = new HashMap<>();
        map.put("retCode", e.getErrorCode());
        map.put("retInfo", e.getMessage());
        return map;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log(e);
        Map<String, Object> map = new HashMap<>();
        map.put("retCode", Constant.RET_CODE_DEBUG);
        map.put("retInfo", "系统错误，请联系管理员");
        return map;
    }

    private void log(Exception e) {
        logger.error("************************异常开始*******************************");
        logger.error(e.toString(), e);
        logger.error("************************异常结束*******************************");
    }
}
