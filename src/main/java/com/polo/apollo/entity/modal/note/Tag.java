package com.polo.apollo.entity.modal.note;

import com.polo.apollo.common.entity.BaseEntity;
import lombok.Data;

/**
 * @author baoqianyong
 * @date 2019/05/19
 */
@Data
public class Tag extends BaseEntity {
    /**
     * 标签名
     */
    private String name;
}
