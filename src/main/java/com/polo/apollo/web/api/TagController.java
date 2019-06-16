package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.modal.note.Tag;
import com.polo.apollo.service.note.TagService;
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

    @PostMapping("save")
    public Result save(Tag tag){
        tagService.save(tag);
        return Result.success(tag);
    }

    @GetMapping("delete/{uid}")
    public Result save(@PathVariable String uid){
        tagService.deleteByUid(uid);
        return Result.success();
    }

    @GetMapping("getTags")
    public Result getTags(){
        return Result.success(tagService.queryList());
    }
}
