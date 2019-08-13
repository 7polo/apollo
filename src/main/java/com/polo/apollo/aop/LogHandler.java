package com.polo.apollo.aop;

import com.polo.apollo.common.util.OkHttpUtil;
import com.polo.apollo.common.util.Utils;
import com.polo.apollo.entity.modal.system.LogRecord;
import com.polo.apollo.service.sytem.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author baoqianyong
 * @date 2019/06/17
 */
@Component
public class LogHandler {

    private static final int capacity = 100;

    /**
     * 存储IP地址
     */
    private Map<String, Map<String, String>> ipMaps = new HashMap<>();

    @Value("${polo.ip-get-url}")
    private String ipGetUrl;

    @Value("#{'${polo.local-ips}'.split(',')}")
    private List<String> localIps;

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

        if (localIps.contains(ip)) {
            return new LogRecord().setIp(ip);
        }
        Map<String, String> ipData = ipMaps.get(ip);
        if (ipData == null) {
            if (StringUtils.hasLength(ipGetUrl)) {
                synchronized (this) {
                    // 发送 http 请求， 并转换为 json 对象
                    Map<String, Object> json = Utils.json2Obj(OkHttpUtil.get(String.format(ipGetUrl, ip)), Map.class);
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
            }
        }

        if (ipData != null) {
            return new LogRecord()
                    .setCountry(ipData.get("country"))
                    .setRegion(ipData.get("region"))
                    .setCity(ipData.get("city"))
                    .setIsp(ipData.get("isp"));
        }
        return null;
    }
}
