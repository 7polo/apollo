package com.polo.apollo.aop;

import com.polo.apollo.common.util.WebUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author baoqianyong
 * @date 2019/06/17
 */
@Component
@Aspect
public class LogAspect {

    @Autowired
    private LogHandler handler;

    @Autowired
    private HttpServletRequest req;

    @Around("@annotation(com.polo.apollo.aop.Log)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        handler.handler(point, req.getRequestURI(), WebUtils.getIp(req));
        return result;
    }
}
