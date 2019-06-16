package com.polo.apollo.entity.modal.system;

import com.polo.apollo.common.entity.BaseEntity;
import lombok.Data;

/**
 * 数据字典的作用
 * @author baoqianyong
 * @date 2019/06/16
 */
@Data
public class DataDic extends BaseEntity {

    /**
     * name
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 对应的值
     */
    private String value;
}
