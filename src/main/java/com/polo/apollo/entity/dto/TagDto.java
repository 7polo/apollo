package com.polo.apollo.entity.dto;

import com.polo.apollo.entity.modal.note.Tag;
import lombok.Data;

/**
 * @author baoqianyong
 * @date 2019/06/03
 */
@Data
public class TagDto extends Tag {

    /**
     * 总数
     */
    private int count;

    /**
     * noteId
     */
    private String noteId;
}
