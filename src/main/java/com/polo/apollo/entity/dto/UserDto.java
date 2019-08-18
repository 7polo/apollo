package com.polo.apollo.entity.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author baoqianyong
 */
@Data
public class UserDto {

    private String uid;

    private String username;

    private String salt;

    private String email;

    /**
     * 头像
     */
    private String icon;

    /**
     * 技能
     */
    private Set<String> skills = new HashSet<>();

    /**
     * 格言
     */
    private String saying;
}
