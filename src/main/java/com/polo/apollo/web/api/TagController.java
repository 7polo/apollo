package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.modal.note.Tag;
import com.polo.apollo.service.note.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
@RequestMapping("tag")
@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "保存 tag")
    @PostMapping("save")
    public Result save(Tag tag) {
        tagService.save(tag);
        return Result.success(tag);
    }

    @ApiOperation(value = "根据 主键id 删除 tag")
    @GetMapping("delete/{uid}")
    public Result save(@PathVariable String uid) {
        tagService.deleteByUid(uid);
        return Result.success();
    }

    @ApiOperation(value = "获取所有标签")
    @GetMapping("getTags")
    public Result getTags() {
        return Result.success(tagService.queryList());
    }
}
