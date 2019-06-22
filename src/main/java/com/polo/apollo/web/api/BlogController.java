package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.modal.blog.Blog;
import com.polo.apollo.entity.vo.BlogVo;
import com.polo.apollo.service.blog.BlogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author baoqianyong
 */
@RestController
@RequestMapping("blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @ApiOperation(value = "保存blog")
    @PostMapping("save")
    public Result saveBlog(Blog blog) {
        blogService.saveBlog(blog);
        return Result.success(blog);
    }

    @ApiOperation(value = "分页查询blog")
    @GetMapping("queryPage")
    public Result queryPage(BlogVo blogVo, int start, int limit) {
        return Result.success(blogService.queryPage(blogVo, start, limit));
    }

    @ApiOperation(value = "根据主键id 删除 blog")
    @PostMapping("delete/{blogId}")
    public Result delete(@PathVariable String blogId) {
        blogService.deleteById(blogId);
        return Result.success();
    }

    @ApiOperation(value = "根据主键id 点赞blog")
    @GetMapping("good/{blogId}")
    public Result good(@PathVariable String blogId) {
        blogService.updateBlogGood(blogId);
        return Result.success();
    }
}
