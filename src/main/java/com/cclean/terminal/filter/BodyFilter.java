package com.cclean.terminal.filter;

import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.constant.Constant;
import com.cclean.terminal.util.HttpHelper;
import com.cclean.terminal.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter(urlPatterns = "/*")
public class BodyFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(BodyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setCharacterEncoding("utf-8");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("application/json;charset=UTF-8");
        // 防止流读取一次后就没有了, 所以需要将流继续写出去
        HttpServletRequest requestWrapper = new BodyRequestWrapper(request);
        String body = HttpHelper.getBodyString(requestWrapper);

        body = body.replaceAll(" ", "");
        body = body.replaceAll("\t", "");
        body = body.replaceAll("\r", "");
        body = body.replaceAll("\n", "");
        String requestUri = requestWrapper.getRequestURI();
        logger.info("requestUri:{}" , requestUri);
        logger.info("requestBody:{}" ,body);

        if (requestUri.contains("/user/login") || requestUri.contains("user/logout")
                ||requestUri.contains("/user/mobile/login") || requestUri.contains("version") ) {
            filterChain.doFilter(requestWrapper, response);
        } else {
            boolean flag = false;
            String token = requestWrapper.getHeader("token");
            if (StringUtils.isNotEmpty(token)) {
                flag = true;
            }
            if (flag) {
                // 接口访问模式（仅开发，正式去除）
                String mode = requestWrapper.getHeader("mode");
                if ("dev".equalsIgnoreCase(mode)) {
                    filterChain.doFilter(requestWrapper, response);
                }else if ("mobile".equalsIgnoreCase(mode)) {
                    filterChain.doFilter(requestWrapper, response);
                } else {
                    String cpuId = requestWrapper.getHeader("cpuId");
                    String macAddress = requestWrapper.getHeader("macAddress");
                    String diskId = requestWrapper.getHeader("diskId");
                    String computerName = requestWrapper.getHeader("computerName");
                    logger.info("requestHeader:cpuId:{},macAddress:{},diskId:{},computerName:{}",cpuId,macAddress,diskId,computerName);
                    // 授权码
                    String authCode = requestWrapper.getHeader("authCode");
                    if (StringUtils.isEmpty(authCode)) authCode = "";
                    // 校验码
                    String authKey = DigestUtils.md5Hex(Constant.CHAOJIE + authCode + Constant.ADX).toLowerCase();
                    // 待验证签名
                    String sign = requestWrapper.getHeader("sign");
                    if (StringUtils.isEmpty(sign)) sign = "";
                    // 计算签名
                    String dSign = DigestUtils.md5Hex(body + authCode + authKey).toLowerCase();
                    if (dSign.equalsIgnoreCase(sign)) {
                        filterChain.doFilter(requestWrapper, response);
                    } else {
                        response.setStatus(403);
                        Map<String, Object> map = new HashMap<>();
                        map.put("retCode", "10404");
                        map.put("retInfo", "设备授权码验证失败");
                        response.getWriter().print(JSONObject.toJSON(map));
                    }
                }
            } else {
                response.setStatus(200);
                Map<String, Object> map = new HashMap<>();
                map.put("retCode", "10403");
                map.put("retInfo", "token验证失败，请重新登录");
                response.getWriter().print(JSONObject.toJSON(map));
            }
        }
    }

    @Override
    public void destroy() {

    }

}
