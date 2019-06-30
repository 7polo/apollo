package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.vo.NoteVo;
import com.polo.apollo.service.note.NoteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author baoqianyong
 * @date 2019/06/30
 */
@RestController
@RequestMapping("blog")
public class BlogController {

    @Autowired
    private NoteService noteService;

    @ApiOperation(value = "分页查询分享的note")
    @GetMapping("queryPage")
    public Result queryPage(NoteVo vo, int start, int limit) {
        return Result.success(noteService.queryBlogPage(vo, start, limit));
    }

    @ApiOperation(value = "根据主键id 点赞blog")
    @GetMapping("good/{blogId}")
    public Result good(@PathVariable String blogId) {
        noteService.addGood(blogId);
        return Result.success();
    }
}
