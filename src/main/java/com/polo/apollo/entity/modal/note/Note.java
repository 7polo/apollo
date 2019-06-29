package com.polo.apollo.entity.modal.note;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.polo.apollo.common.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

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

    /**
     * 发布时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date publishDt;

    /**
     * 阅读数
     */
    private int readCount;

    /**
     * 赞
     */
    private int good;

}
