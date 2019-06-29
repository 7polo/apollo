package com.polo.apollo.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polo.apollo.common.entity.Tree;

import java.io.IOException;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * @author baoqianyong
 * @date 2019/05/25
 */
public class Utils {

    public static boolean isEmpty(Collection c) {
        return c == null || c.size() == 0;
    }

    /**
     * 根据 list 构建树
     *
     * @param list
     * @param parentBi 节点关系比较器
     * @return
     */
    public static List<Tree> buildTree(List list, BiPredicate<Tree, Tree> parentBi) {

        List<Tree> treeList = new ArrayList<>();
        for (Object obj : list) {
            treeList.add(Utils.obj2Obj(obj, Tree.class));
        }
        for (Tree pNode : treeList) {
            for (Tree cNode : treeList) {
                if (pNode == cNode) {
                    continue;
                }
                if (parentBi.test(pNode, cNode)) {
                    pNode.addChildren(cNode);
                    cNode.setHasParent(true);
                }
            }
        }
        // 过滤出顶层的父节点
        return treeList.stream().filter(tree -> !tree.hasParent()).collect(Collectors.toList());
    }

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
