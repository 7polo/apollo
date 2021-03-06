package com.polo.apollo.entity.modal.system;

import com.polo.apollo.common.entity.BaseEntity;
import lombok.Data;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
@Data
public class User extends BaseEntity {

    private String username;

    private String password;

    private String salt;

    private String email;

    /**
     * 头像
     */
    private String icon;

    /**
     * 技能
     */
    private String skills;

    /**
     * 格言
     */
    private String saying;
}
