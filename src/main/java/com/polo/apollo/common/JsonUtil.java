package com.polo.apollo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * @author baoqianyong
 * @date 2019/05/29
 */
public class JsonUtil {

    /**
     * obj 转成json
     *
     * @param obj
     * @return
     */
    public static String obj2Json(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
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
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, clz);
        } catch (IOException e) {
            e.printStackTrace();
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
        ObjectMapper oMapper = new ObjectMapper();
        // 忽略 null 字段
        oMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        return oMapper.convertValue(obj, Map.class);
    }

    /**
     * object to obj
     *
     * @param obj
     * @return
     */
    public static <T> T obj2Obj(Object obj, Class<T> clz) {
        ObjectMapper oMapper = new ObjectMapper();
        // 忽略 null 字段
        oMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        return oMapper.convertValue(obj, clz);
    }
}
