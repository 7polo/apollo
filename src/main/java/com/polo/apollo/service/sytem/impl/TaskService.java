package com.polo.apollo.service.sytem.impl;

import com.polo.apollo.service.sytem.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author baoqianyong
 * @date 2019/06/18
 */
@Service
public class TaskService {

    @Autowired
    private LogService logService;

    /**
     * 5 分钟 记录一次
     */
    @Scheduled(fixedRate = 60000 * 5)
    private void autoSave() {
        logService.saveLog(logService.getAllLog());
    }
}
