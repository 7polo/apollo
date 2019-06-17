package com.polo.apollo.aop;

import com.polo.apollo.common.Utils;
import com.polo.apollo.common.WebUtils;
import com.polo.apollo.entity.modal.system.LogRecord;
import com.polo.apollo.service.sytem.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author baoqianyong
 * @date 2019/06/17
 */
@Component
public class LogHandler {

    @Autowired
    private LogService logService;

    @Async
    public void handler(ProceedingJoinPoint point, String url, String ip) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Log logAnno = method.getAnnotation(Log.class);
        LogRecord log = new LogRecord();
        log.setUid(Utils.uuid());
        log.setCreateDt(new Date());
        log.setUrl(url);
        log.setName(logAnno.value());
        log.setIp(ip);

        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logService.addLog(log);
        System.out.println("asdasdasasdsd");
    }
}
