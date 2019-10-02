package com.polo.apollo.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author baoqianyong
 * @date 2019/05/25
 */
public class Utils {

    public static boolean isEmpty(Collection c) {
        return c == null || c.size() == 0;
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取uuid
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * obj 转成json
     *
     * @param obj
     * @return
     */
    public static String obj2Json(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json 转换成 obj
     *
     * @param json json 字符串
     * @param clz  java class
     * @param <T>  泛型
     * @return
     */
    public static <T> T json2Obj(String json, Class<T> clz) {
        try {
            return objectMapper.readValue(json, clz);
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * object to map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> obj2Map(Object obj) {
        return objectMapper.convertValue(obj, Map.class);
    }

    /**
     * object to obj
     *
     * @param obj
     * @return
     */
    public static <T> T obj2Obj(Object obj, Class<T> clz) {
        return objectMapper.convertValue(obj, clz);
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String firstUpper(String str) {
        if (StringUtils.hasLength(str)) {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        return str;
    }

    public static List<String> getDateRange(LocalDate date, long size, ChronoUnit unit) {
        String pattern = "YYYY-MM-dd";
        switch (unit) {
            case MONTHS:
                pattern = "YYYY-MM";
                break;
            case YEARS:
                pattern = "YYYY";
                break;
            default:
                break;
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        int step = size < 0 ? -1 : 1;
        size = Math.abs(size);
        List<String> list = new ArrayList<>();
        list.add(date.format(fmt));
        for (int i = 0; i < size; i++) {
            date = date.plus(step, unit);
            list.add(date.format(fmt));
        }
        if (step < 0) {
            Collections.reverse(list);
        }
        return list;
    }

    public static List<String> getWeekDays(LocalDate date) {
//        date = date.plus(-7, ChronoUnit.DAYS);
        int days = 7 - date.getDayOfWeek().getValue();
        date = date.plus(days, ChronoUnit.DAYS);
        return Utils.getDateRange(date, -7, ChronoUnit.DAYS);
    }
}
