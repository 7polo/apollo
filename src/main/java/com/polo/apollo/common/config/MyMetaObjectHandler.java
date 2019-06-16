package com.polo.apollo.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author baoqianyong
 * @date 2019/05/13
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setInsertFieldValByName("createDt", new Date(), metaObject);
        if (this.getFieldValByName("deleted", metaObject) == null) {
            this.setInsertFieldValByName("deleted", true, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
