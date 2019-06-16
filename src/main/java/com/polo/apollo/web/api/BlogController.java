package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.modal.blog.Blog;
import com.polo.apollo.entity.modal.note.Note;
import com.polo.apollo.entity.vo.BlogVo;
import com.polo.apollo.service.blog.BlogService;
import com.polo.apollo.service.sytem.SeoService;
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

    @Autowired
    private SeoService seoService;

    @PostMapping("create")
    public Result createBlog(Note note) {

        if (blogService.queryBlogNoteId(note.getUid()) != null) {
            return Result.error("该笔已分享过， 无需再次分享");
        }
        Blog blog = blogService.createBlogWithNote(note);
        return Result.success(blog);
    }

    @PostMapping("save")
    public Result saveBlog(Blog blog) {
        blogService.saveBlog(blog);
        return Result.success(blog);
    }

    @GetMapping("queryPage")
    public Result queryPage(BlogVo blogVo, int start, int limit) {
        return Result.success(blogService.queryPage(blogVo, start, limit));
    }

    @PostMapping("delete/{blogId}")
    public Result delete(@PathVariable String blogId) {
        blogService.deleteById(blogId);
        return Result.success();
    }

    @GetMapping("good/{blogId}")
    public Result good(@PathVariable String blogId) {
        blogService.updateBlogGood(blogId);
        return Result.success();
    }
}
