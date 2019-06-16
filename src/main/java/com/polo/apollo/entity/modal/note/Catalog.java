package com.polo.apollo.entity.modal.note;

import com.polo.apollo.common.entity.BaseEntity;
import lombok.Data;

/**
 * 目录
 * @author baoqianyong
 * @date 2019/05/19
 */
@Data
public class Catalog extends BaseEntity {

    /**
     * root 节点
     */
    public static final String ROOT = "root";

    /**
     * 所属目录id， 父节点id
     */
    private String dirId;

    private String name;
}
