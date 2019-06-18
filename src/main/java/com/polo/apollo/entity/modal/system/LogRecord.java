package com.polo.apollo.entity.modal.system;

import com.polo.apollo.common.entity.BaseEntity;
import lombok.Data;

/**
 * @author baoqianyong
 * @date 2019/06/17
 */
@Data
public class LogRecord extends BaseEntity {

    private String name;

    private String url;

    private String ip;

    /**
     * 国家
     */
    private String country;

    /**
     * 省
     */
    private String region;

    /**
     * 市
     */
    private String city;

    /**
     * 运营商
     */
    private String isp;
}
