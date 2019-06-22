package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.modal.system.Seo;
import com.polo.apollo.service.sytem.SeoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author baoqianyong
 * @date 2019/06/14
 */
@RestController
@RequestMapping("seo")
public class SeoController {

    @Autowired
    private SeoService seoService;

    @ApiOperation(value = "保存、更新 seo")
    @PostMapping("save")
    public Result save(Seo seo) {
        seoService.saveOrUpdateSeo(seo, seo.getType());
        return Result.success();
    }

    @ApiOperation(value = "根据类型 和 关联id查询 seo")
    @GetMapping("/{type}/{relateId}")
    public Result getSeo(@PathVariable String type, @PathVariable String relateId) {
        return Result.success(seoService.querySeoByRelateId(type, relateId));
    }
}
