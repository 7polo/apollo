package com.polo.apollo.common.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/06/11
 */
public class Tree extends HashMap<String, Object> {

    private boolean leaf = true;

    private boolean hasParent = false;

    public void addChildren(Tree node) {
        List<Tree> children = (List<Tree>) this.get("children");
        if (children == null) {
            children = new ArrayList<>();
            this.put("children", children);
        }
        children.add(node);
    }

    public void setHasParent(boolean has) {
        this.hasParent = has;
    }

    /**
     * 是否是叶子节点
     *
     * @return
     */
    public boolean isLeaf() {
        return this.leaf;
    }

    /**
     * 是否存在父节点
     *
     * @return
     */
    public boolean hasParent() {
        return this.hasParent;
    }
}
