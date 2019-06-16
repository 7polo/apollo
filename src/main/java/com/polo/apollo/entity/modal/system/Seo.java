package com.polo.apollo.entity.modal.system;


import com.polo.apollo.common.entity.BaseEntity;
import lombok.Data;

/**
 * @author baoqianyong
 */
@Data
public class Seo extends BaseEntity {

    /**
     * 关联id
     */
    private String relateId;

    private String type;

    private String description;

    private String keyword;
}
