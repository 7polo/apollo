package com.polo.apollo.entity.dto;

import com.polo.apollo.entity.modal.blog.Blog;
import com.polo.apollo.entity.modal.note.Tag;
import lombok.Data;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/05/27
 */
@Data
public class BlogDto extends Blog {

    /**
     * 目录id
     */
    private String dirId;

    /**
     * 目录名称
     */
    private String dirName;

    /**
     * 标题
     */
    private String name;

    /**
     * 作者
     */
    private String authorName;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签
     */
    private List<Tag> tags;

    /**
     * 标签 tag1,tag2
     */
    private String tagNames;
}
