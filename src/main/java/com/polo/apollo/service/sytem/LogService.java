package com.polo.apollo.service.sytem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polo.apollo.entity.modal.system.LogRecord;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/06/17
 */
public interface LogService {

    /**
     * 添加访问记录， 放置到缓冲队列中
     *
     * @param logRecord
     */
    void addLog(LogRecord logRecord);

    /**
     * 保存记录到数据库
     * @param logRecordList
     */
    void saveLog(List<LogRecord> logRecordList);

    /**
     * 分页查询
     *
     * @param log
     * @param start
     * @param limit
     * @return
     */
    Page<LogRecord> queryPage(LogRecord log, int start, int limit);
}
