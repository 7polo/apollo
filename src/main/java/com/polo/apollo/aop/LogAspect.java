package com.polo.apollo.aop;

import com.polo.apollo.common.Utils;
import com.polo.apollo.common.WebUtils;
import com.polo.apollo.entity.modal.system.LogRecord;
import com.polo.apollo.service.sytem.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author baoqianyong
 * @date 2019/06/17
 */
@Component
@Aspect
public class LogAspect {

    @Autowired
    private LogService logService;

    @Autowired
    private HttpServletRequest req;

    @Around("@annotation(com.polo.apollo.aop.Log)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        logService.addLog(getLogRecord(point));
        return result;
    }

    private LogRecord getLogRecord(ProceedingJoinPoint point) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Log logAnno = method.getAnnotation(Log.class);
        LogRecord log = new LogRecord();
        log.setUid(Utils.uuid());
        log.setCreateDt(new Date());
        log.setUrl(req.getRequestURI());
        log.setName(logAnno.value());
        log.setIp(WebUtils.getIp(req));
        return log;
    }
}
