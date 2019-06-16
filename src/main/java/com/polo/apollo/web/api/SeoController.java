package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.modal.system.Seo;
import com.polo.apollo.service.sytem.SeoService;
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

    @PostMapping("save")
    public Result save(Seo seo) {
        seoService.saveOrUpdateSeo(seo, seo.getType());
        return Result.success();
    }

    @GetMapping("/{type}/{relateId}")
    public Result getSeo(@PathVariable String type, @PathVariable String relateId) {
        return Result.success(seoService.querySeoByRelateId(type, relateId));
    }
}
