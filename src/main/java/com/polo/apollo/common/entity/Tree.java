package com.polo.apollo.common.entity;

import com.polo.apollo.common.util.Utils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author baoqianyong
 * @date 2019/06/11
 */
@Slf4j
public class Tree extends HashMap<String, Object> {


    private static final String CHILDREN = "children";

    private boolean hasParent = false;

    public void addChildren(Tree node) {
        List<Tree> children = (List<Tree>) this.get(CHILDREN);
        if (children == null) {
            children = new ArrayList<>();
            this.put(CHILDREN, children);
        }
        children.add(node);
    }

    /**
     * 根据 list 构建树
     *
     * @param list       形式数据
     * @param id         唯一标识
     * @param func       返回父节点
     * @param isDataSort list 是否是相对有序的（子节点在父节点后）
     * @return
     */
    public static List<Tree> buildTree(List list, String id, Function<Tree, Object> func, boolean isDataSort) {
        long start = System.currentTimeMillis();
        //顶级节点
        Map<String, Tree> noParentTreeMap = new LinkedHashMap<>(list.size());

        Map<String, Tree> treeMap = new HashMap<>(list.size());
        for (Object obj : list) {
            Tree tree = Utils.obj2Obj(obj, Tree.class);
            // 唯一标识
            String treeCode = tree.get(id).toString();

            // 父节点的 code
            Object parentCodeObj = func.apply(tree);
            if (parentCodeObj != null) {
                String parentCode = parentCodeObj.toString();
                // 尝试去获取父节点
                Tree parent = treeMap.get(parentCode);
                if (parent != null) {
                    parent.addChildren(tree);
                } else {
                    noParentTreeMap.put(treeCode, tree);
                }
            } else {
                noParentTreeMap.put(treeCode, tree);
            }

            // 把当前节点放置到map中
            treeMap.put(treeCode, tree);
        }

        // --- 如果 list 数据不是相对有序（子节点在父节点之后）， 则需要再次处理下 noParentTreeMap
        if (!isDataSort) {
            Map<String, Tree> tempMap = new LinkedHashMap<>(noParentTreeMap.size());
            for (Map.Entry<String, Tree> entry : noParentTreeMap.entrySet()) {
                // 父节点的 code
                Object parentCodeObj = func.apply(entry.getValue());
                if (parentCodeObj != null) {
                    String parentCode = parentCodeObj.toString();
                    // 父节点的 code
                    // 尝试去获取父节点
                    Tree parent = treeMap.get(parentCode);
                    if (parent != null) {
                        // 挂载到父节点上
                        parent.addChildren(entry.getValue());
                    } else {
                        tempMap.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            noParentTreeMap = tempMap;
        }

        // 将顶级节点拿出来
        List<Tree> treeList = new ArrayList<>(noParentTreeMap.values());
        log.info(String.format("构建树 ===> 节点个数 %s 个, 耗时 %s ms", list.size(), (System.currentTimeMillis() - start)));
        return treeList;
    }

    /**
     * 根据 list 构建树  子父节点相对有序
     *
     * @param list
     * @param id
     * @param func
     * @return
     */
    public static List<Tree> buildTree(List list, String id, Function<Tree, Object> func) {
        return buildTree(list, id, func, true);
    }
}
