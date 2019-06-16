package com.polo.apollo.common;

import com.polo.apollo.entity.dto.Tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
            treeList.add(JsonUtil.obj2Obj(obj, Tree.class));
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
}
