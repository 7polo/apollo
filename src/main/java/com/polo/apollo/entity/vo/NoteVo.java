package com.polo.apollo.entity.vo;

import lombok.Data;

/**
 * @author baoqianyong
 * @date 2019/06/30
 */
@Data
public class NoteVo {

    /**
     * 是否发布了
     * null： 不进行过滤
     * true： 发布了
     * false： 未发布
     */
    private Boolean published;


    /**
     * 标签名
     */
    private String tagName;

    /**
     * 是否压缩 content
     */
    private boolean abbre = false;
}
