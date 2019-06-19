package com.polo.apollo.service.sytem.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polo.apollo.common.util.Utils;
import com.polo.apollo.dao.system.LogDao;
import com.polo.apollo.entity.modal.system.LogRecord;
import com.polo.apollo.service.sytem.LogService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author baoqianyong
 * @date 2019/06/17
 */
@Service
@Log
public class LogServiceImpl implements LogService {

    /**
     * 容量为 50 的队列
     */
    private ArrayBlockingQueue<LogRecord> queue = new ArrayBlockingQueue<>(50);

    @Autowired
    private LogDao logDao;

    @Override
    public void addLog(LogRecord logRecord) {
        if (queue.remainingCapacity() < 1) {
            // 容量不足时， 将所有的元素取出存储
            this.saveLog(getAllLog());
        }
        queue.offer(logRecord);
    }

    /**
     * 取出队列全部的元素
     *
     * @return
     */
    @Override
    public List<LogRecord> getAllLog() {
        List<LogRecord> logRecordList = new ArrayList<>();
        queue.drainTo(logRecordList);
        return logRecordList;
    }

    @Override
    public void saveLog(List<LogRecord> list) {
        if (!Utils.isEmpty(list)) {
            logDao.batchInsert(list);
        }
    }

    @Override
    public Page<LogRecord> queryPage(LogRecord log, int start, int limit) {
        LambdaQueryWrapper<LogRecord> query = new LambdaQueryWrapper<>();
        if (log != null) {
            if (StringUtils.hasLength(log.getName())) {
                query.eq(LogRecord::getName, log.getName());
            }
        }
        query.orderByDesc(LogRecord::getCreateDt);
        return (Page<LogRecord>) logDao.selectPage(new Page<>(start, limit), query);
    }

}
