package com.polo.apollo.entity.dto;

import com.polo.apollo.entity.modal.note.Note;
import com.polo.apollo.entity.modal.note.Tag;
import lombok.Data;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
@Data
public class NoteDto extends Note {

    /**
     * 目录名称
     */
    private String catalogName;

    /**
     * 作者
     */
    private String authorName;

    /**
     * 标签
     */
    private List<Tag> tags;

    /**
     * 标签名： tag1,tag2....
     */
    private String tagNames;

    /**
     * 是否是上一篇
     */
    private Boolean isPre;
}
