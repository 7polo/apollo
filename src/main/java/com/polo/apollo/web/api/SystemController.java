package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.modal.system.DataDic;
import com.polo.apollo.entity.modal.system.SysConfig;
import com.polo.apollo.entity.vo.BlogVo;
import com.polo.apollo.service.sytem.DataDicService;
import com.polo.apollo.service.sytem.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author baoqianyong
 * @date 2019/06/14
 */
@RestController
@RequestMapping("sys")
public class SystemController {

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private DataDicService dataDicService;

    @PostMapping("save")
    public Result save(SysConfig config) {
        sysConfigService.updateConfig(config);
        return Result.success(sysConfigService.getSysConfig());
    }

    @PostMapping("/datadic/save")
    public Result dataDicSave(DataDic dataDic) {
        dataDicService.saveOrUpdate(dataDic);
        return Result.success();
    }

    @PostMapping("/datadic/delete/{uid}")
    public Result dataDicSave(@PathVariable String uid) {
        dataDicService.delete(uid);
        return Result.success();
    }

    @GetMapping("/datadic/queryPage")
    public Result dataDicQueryPage(DataDic dic, int start, int limit) {
        return Result.success(dataDicService.queryPage(dic, start, limit));
    }
}
