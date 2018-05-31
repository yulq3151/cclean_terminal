package com.cclean.terminal.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 网络请求工具类
 */
public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final String MODE = "mode";
    private static final String TOKEN = "token";
    private static final String AUTH_CODE = "authCode";
    private static final String SIGN = "sign";

    // 设置 Header
    private static void addHeaders(HttpPost httpPost, HttpServletRequest request) {
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.addHeader(MODE, request.getHeader(MODE));
        httpPost.addHeader(TOKEN, request.getHeader(TOKEN));
        httpPost.addHeader(AUTH_CODE, request.getHeader(AUTH_CODE));
        httpPost.addHeader(SIGN, request.getHeader(SIGN));
    }

    // 设置 Header
    private static void addHeaders(HttpPost httpPost, String token) {
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.addHeader(TOKEN, token);
    }

    // 请求返回Response
    public static CloseableHttpResponse doPost(String url, HttpServletRequest request) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        addHeaders(httpPost, request);
        httpPost.setEntity(new InputStreamEntity(request.getInputStream()));
        return httpClient.execute(httpPost);
    }

    // 请求返回body String
    public static String doPostStr(String url, HttpServletRequest request) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        addHeaders(httpPost, request);
        httpPost.setEntity(new InputStreamEntity(request.getInputStream()));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        response.close();
        httpClient.close();
        return EntityUtils.toString(entity, "UTF-8");
    }

    // 请求返回Response,参数名做相应改变
    public static CloseableHttpResponse doPost(String url, HttpServletRequest request, String params) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        addHeaders(httpPost, request);
        httpPost.setEntity(new StringEntity(params, "UTF-8"));
        //httpPost.setEntity(new InputStreamEntity(request.getInputStream()));
        return httpClient.execute(httpPost);
    }

    public static String doPost(String url, JSONObject jsonParam) {
        return doPost(url, "", jsonParam);
    }

    public static String doPost(String url, String token, JSONObject jsonParam) {
        if (url == null) {
            return null;
        }
        logger.info("请求基础服务开始：{} -url:{},请求token：{},请求参数：{}", System.currentTimeMillis(),url, token, jsonParam);
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        //HttpPost httppost = new HttpPost("http://cloud.cclean.cn:81/cloud/server/v1/order/info");
        HttpPost httppost = new HttpPost(url);

        //jsonParam.put("id", "cd63c8b94084459d99eaab01d72cc0f0");
        StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        try {
            httppost.setEntity(entity);
            if (!token.equals("")) {
                httppost.setHeader("accessToken", token);
                httppost.setHeader("token", token);
            }
            httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entitys = response.getEntity();
                if (entitys != null) {
                    String msg = EntityUtils.toString(entitys, "UTF-8");
                    logger.info("请求基础服务结束：{},-返回结果：{}", System.currentTimeMillis(),msg);
                    return msg;
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(),e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(),e);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        }
        return null;
    }

}
