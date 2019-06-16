package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.modal.system.SysConfig;
import com.polo.apollo.service.sytem.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author baoqianyong
 * @date 2019/06/14
 */
@RestController
@RequestMapping("sys")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @PostMapping("save")
    public Result save(SysConfig config) {
        sysConfigService.updateConfig(config);
        return Result.success(sysConfigService.getSysConfig());
    }
}
