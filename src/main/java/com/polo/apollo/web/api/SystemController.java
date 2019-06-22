package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.modal.system.DataDic;
import com.polo.apollo.entity.modal.system.LogRecord;
import com.polo.apollo.entity.modal.system.SysConfig;
import com.polo.apollo.service.sytem.DataDicService;
import com.polo.apollo.service.sytem.LogService;
import com.polo.apollo.service.sytem.SysConfigService;
import io.swagger.annotations.ApiOperation;
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

    @Autowired
    private LogService logService;

    @ApiOperation(value = "保存 系统参数配置")
    @PostMapping("save")
    public Result save(SysConfig config) {
        sysConfigService.updateConfig(config);
        return Result.success(sysConfigService.getSysConfig());
    }

    @ApiOperation(value = "保存 数据字典")
    @PostMapping("/dic/save")
    public Result dataDicSave(DataDic dataDic) {
        dataDicService.saveOrUpdate(dataDic);
        return Result.success();
    }

    @ApiOperation(value = "删除 数据字典")
    @PostMapping("/dic/delete/{uid}")
    public Result dataDicSave(@PathVariable String uid) {
        dataDicService.delete(uid);
        return Result.success();
    }

    @ApiOperation(value = "分页查询数据字典")
    @GetMapping("/dic/queryPage")
    public Result dataDicQueryPage(DataDic dic, int start, int limit) {
        return Result.success(dataDicService.queryPage(dic, start, limit));
    }

    @ApiOperation(value = "分页查询日志")
    @GetMapping("/log/queryPage")
    public Result logQueryPage(LogRecord log, int start, int limit) {
        return Result.success(logService.queryPage(log, start, limit));
    }
}
