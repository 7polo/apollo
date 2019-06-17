package com.polo.apollo.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.polo.apollo.entity.modal.system.LogRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/06/17
 */
@Repository
public interface LogDao extends BaseMapper<LogRecord> {

    /**
     * 批量保存
     * @param list
     */
    void batchInsert(@Param("list") List<LogRecord> list);
}
