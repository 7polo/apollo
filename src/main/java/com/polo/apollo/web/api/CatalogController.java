package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.modal.note.Catalog;
import com.polo.apollo.service.note.CatalogService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "保存、更新目录")
    @PostMapping("save")
    public Result save(Catalog catalog) {
        catalogService.save(catalog);
        return Result.success(catalog);
    }

    @ApiOperation(value = "获取当前目录的所")
    @GetMapping("loadChildNode/{dirId}")
    public Result loadChildNode(@PathVariable String dirId) {
        return Result.success(catalogService.loadNodesByDir(dirId));
    }

    @ApiOperation(value = "加载所有目录")
    @GetMapping("loadTreeAll")
    public Result loadTreeAll() {
        return Result.success(catalogService.loadTreeAll());
    }

    @ApiOperation(value = "删除目录")
    @PostMapping("delete/{uid}")
    public Result delete(@PathVariable String uid) {
        catalogService.deleteByUid(uid);
        return Result.success();
    }
}
