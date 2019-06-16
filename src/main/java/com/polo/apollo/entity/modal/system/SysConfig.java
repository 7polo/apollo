package com.polo.apollo.entity.modal.system;

import com.polo.apollo.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 系统参数
 * @author baoqianyong
 * @date 2019/06/12
 */
@Data
public class SysConfig extends BaseEntity {

    /**
     * 网站所有者
     */
    private String userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 邮箱账号
     */
    private String email;

    /**
     * 邮箱密码
     */
    private String emailPass;

    /**
     * 邮箱端口
     */
    private Integer emailPort;

    /**
     * 邮箱是否开启ssl
     */
    private Boolean emailSsl;

    @TableField(exist = false)
    private String description;

    @TableField(exist = false)
    private String keyword;
}
