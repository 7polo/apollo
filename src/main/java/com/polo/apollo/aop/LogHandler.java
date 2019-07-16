package com.polo.apollo.aop;

import com.polo.apollo.common.util.OkHttpUtil;
import com.polo.apollo.common.util.Utils;
import com.polo.apollo.entity.modal.system.LogRecord;
import com.polo.apollo.service.sytem.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author baoqianyong
 * @date 2019/06/17
 */
@Component
public class LogHandler {

    /**
     * 存储IP地址
     */
    private Map<String, Map<String, String>> ipMaps = new ConcurrentHashMap<>();

    private static final int capacity = 100;

    @Autowired
    private LogService logService;

    @Async
    public void handler(ProceedingJoinPoint point, String url, String ip) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Log logAnno = method.getAnnotation(Log.class);
        LogRecord log = getIpInfo(ip);
        log.setUid(Utils.uuid());
        log.setCreateDt(new Date());
        log.setUrl(url);
        log.setName(logAnno.value());
        log.setIp(ip);
        logService.addLog(log);
    }

    /**
     * 获取ip的信息
     *
     * @param ip
     * @return
     */
    private LogRecord getIpInfo(String ip) {
        LogRecord log = new LogRecord();
        Map<String, String> ipData = ipMaps.get(ip);
        if (ipData == null) {
            String str = OkHttpUtil.get("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
            Map<String, Object> json = Utils.json2Obj(str, Map.class);
            if (json != null) {
                ipData = (Map<String, String>) json.get("data");
                if (ipData != null) {
                    if (ipMaps.size() > capacity) {
                        ipMaps.clear();
                    }
                    ipMaps.put(ip, ipData);
                }
            }
        }
        if (ipData != null) {
            log.setCountry(ipData.get("country"));
            log.setRegion(ipData.get("region"));
            log.setCity(ipData.get("city"));
            log.setIsp(ipData.get("isp"));
        }
        return log;
    }
}
