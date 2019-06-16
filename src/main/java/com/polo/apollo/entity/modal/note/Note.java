package com.polo.apollo.entity.modal.note;

import com.polo.apollo.common.entity.BaseEntity;
import lombok.Data;

/**
 * @author baoqianyong
 * @date 2019/05/19
 */
@Data
public class Note extends BaseEntity {

    /**
     * 目录id
     */
    private String dirId;

    /**
     * 标题
     */
    private String name;

    /**
     * 内容
     */
    private String content;

}
