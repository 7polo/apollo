package com.polo.apollo.entity.modal.note;

import com.polo.apollo.common.entity.BaseEntity;
import lombok.Data;

/**
 * note 和 note 关系映射表
 * @author baoqianyong
 * @date 2019/06/02
 */
@Data
public class TagNote extends BaseEntity {

    private String tagId;

    private String noteId;
}
