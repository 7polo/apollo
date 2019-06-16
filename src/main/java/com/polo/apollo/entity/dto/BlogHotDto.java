package com.polo.apollo.entity.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author baoqianyong
 * @date 2019/05/31
 */
@Data
public class BlogHotDto {

    private String uid;

    private String name;

    private Date createDt;

    private int good;

    private int readCount;
}
