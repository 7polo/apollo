package com.polo.apollo.entity.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author baoqianyong
 * @date 2019/06/23
 */
@Data
public class SiteMapDto {

    private String url;

    private Date lastMod;

    private String changefreq;

    private double priority;
}
