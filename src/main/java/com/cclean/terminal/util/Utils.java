package com.cclean.terminal.util;

import com.cclean.terminal.constant.Constant;
import com.cclean.terminal.exception.BusinessException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    public static final Integer pageNum = 1;

    public static final Integer pageSize = 100;

    // 获取uuid
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    // 订单日，格式yyyyMMdd
    public static Integer getOrderDate(){
        return Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
    }

    // 参数为空
    public static void paramIsNull(Object obj) throws BusinessException {
        if (obj == null) {
            throw new BusinessException(Constant.RET_CODE_PARAM_NULL, Constant.RET_INFO_PARAM_NULL);
        }
    }

    // id为空
    public static void idIsNull(String id) throws BusinessException {
        if (id == null || id.length() == 0) {
            throw new BusinessException(Constant.RET_CODE_ID_NULL, Constant.RET_INFO_ID_NULL);
        }
    }

    // id对应的对象为空
    public static void objIsNull(Object obj) throws BusinessException {
        objIsNull(obj, Constant.RET_INFO_OBJ_NULL);
    }

    // id对应的对象为空
    public static void objIsNull(Object obj, String msg) throws BusinessException {
        if (obj == null) {
            throw new BusinessException(Constant.RET_INFO_OBJ_NULL, msg);
        }
    }

    // 当前时间字符串
    public static String formatNow(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    public static Date formatDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static Date formatDate(String format, String dateStr ) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return new Date();
        }
    }

    // 设置起止时间
    public static void setStartAndEndDay(Map<String, Object> map, Date day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        try {
            String dayStr = sdf.format(day);
            Date startTime = sdf.parse(dayStr);
            calendar.setTime(startTime);
            calendar.add(Calendar.DATE, 1);
            Date endTime = calendar.getTime();
            map.put("startTime", startTime);
            map.put("endTime", endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static String dateToString(String format,Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String format1 = sdf.format(date);
        return format1;
    }
    public static Date addDate(Date date,Integer day){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,day);
        return calendar.getTime();
    }
}
