package com.polo.apollo.listener;

import com.polo.apollo.service.sytem.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author baoqianyong
 * @date 2019/06/12
 */
@Component
public class ApplicationReadyEventListener  implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        sysConfigService.load();
    }
}
