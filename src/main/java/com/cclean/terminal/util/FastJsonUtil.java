package com.cclean.terminal.util;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Type;
import java.util.List;

/**
 * FastJson 工具类
 */
public class FastJsonUtil {

    /** 对象转为json */
    public static String toJson(Object object){
        return JSON.toJSONString(object);
    }

    /** json转对象 */
    public static <T> T toObject(String json, Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }

    /**
     * json转对象
     * Type type = new TypeReference<T>() {}.getType();
     */
    public static <T> T toObject(String json, Type type){
        return JSON.parseObject(json, type);
    }

    /** json转集合 */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

}
