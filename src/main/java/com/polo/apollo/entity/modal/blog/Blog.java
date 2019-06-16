package com.polo.apollo.entity.modal.blog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.polo.apollo.common.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * blog 是对外开放的note
 * @author baoqianyong
 */
@Data
public class Blog extends BaseEntity {

    /**
     * noye的id
     */
    private String noteId;

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
