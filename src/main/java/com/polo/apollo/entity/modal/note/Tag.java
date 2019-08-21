package com.polo.apollo.entity.modal.note;

import com.polo.apollo.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author baoqianyong
 * @date 2019/05/19
 */
@Data
@Accessors(chain = true)
public class Tag extends BaseEntity {
    /**
     * 标签名
     */
    private String name;
}
