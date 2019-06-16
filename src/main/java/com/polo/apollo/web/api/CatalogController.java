package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.modal.note.Catalog;
import com.polo.apollo.service.note.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author baoqianyong
 * @date 2019/05/24
 */
@RestController
@RequestMapping("catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @PostMapping("save")
    public Result save(Catalog catalog) {
        catalogService.save(catalog);
        return Result.success(catalog);
    }

    @GetMapping("loadChildNode/{dirId}")
    public Result loadChildNode(@PathVariable String dirId) {
        return Result.success(catalogService.loadNodesByDir(dirId));
    }

    @GetMapping("loadTreeAll")
    public Result loadTreeAll() {
        return Result.success(catalogService.loadTreeAll());
    }


    @PostMapping("delete/{uid}")
    public Result delete(@PathVariable String uid) {
        catalogService.deleteByUid(uid);
        return Result.success();
    }


}
