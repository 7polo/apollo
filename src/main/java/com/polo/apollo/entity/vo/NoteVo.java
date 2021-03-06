package com.polo.apollo.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author baoqianyong
 * @date 2019/06/30
 */
@Data
@Accessors(chain = true)
public class NoteVo {

    /**
     * 是否发布了
     * null： 不进行过滤
     * true： 发布了
     * false： 未发布
     */
    private Boolean published;

    private String name;

    private String content;

    /**
     * 查询内容（搜索范围 标签、标题、内容）
     */
    private String search;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 是否压缩 content
     * null： 不查询
     * true： 压缩
     * false： 不压缩
     */
    private Boolean abbre = false;
}
