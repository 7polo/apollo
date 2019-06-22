package com.polo.apollo.service.blog.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polo.apollo.common.Constant;
import com.polo.apollo.dao.blog.BlogDao;
import com.polo.apollo.entity.dto.BlogDto;
import com.polo.apollo.entity.dto.BlogHotDto;
import com.polo.apollo.entity.modal.blog.Blog;
import com.polo.apollo.entity.modal.note.Note;
import com.polo.apollo.entity.vo.BlogVo;
import com.polo.apollo.service.blog.BlogService;
import com.polo.apollo.service.note.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author baoqianyong
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private TagService tagService;

    @Autowired
    private HttpSession httpSession;

    @Override
    public void saveBlog(Blog blog) {
        if (StringUtils.hasLength(blog.getUid())) {
            if (blogDao.selectById(blog.getUid()) != null) {
                blogDao.updateById(blog);
                return;
            }
        }
        blogDao.insert(blog);
    }

    @Override
    public BlogDto queryById(String uid) {
        BlogDto blog = blogDao.queryBlogById(uid);
        if (blog != null) {
            blog.setTags(tagService.queryByNoteId(blog.getNoteId()));
        }
        return blog;
    }

    @Override
    public Page<BlogDto> queryPage(BlogVo blogVo, int start, int limit) {
        Page<BlogDto> blogDtoPage = (Page<BlogDto>) blogDao.queryBlogPage(new Page<>(start, limit), blogVo);
        return blogDtoPage;
    }

    @Override
    public Blog createBlogWithNote(Note note) {

        Blog blog = queryBlogNoteId(note.getUid());
        if (blog == null) {
            blog = new Blog();
            blog.setNoteId(note.getUid());
            blog.setPublishDt(new Date());
            blogDao.insert(blog);
        }
        return blog;
    }

    /**
     * 查询当前博客的上下篇
     *
     * @param
     * @return
     */
    @Override
    public List<BlogDto> queryPreAndNextBlog(Blog blog) {
        return blogDao.queryPreAndNextBlog(blog.getUid(), blog.getCreateDt());
    }

    @Override
    public Blog queryBlogNoteId(String noteId) {
        Wrapper<Blog> queryWrapper = new LambdaQueryWrapper<Blog>()
                .eq(Blog::getNoteId, noteId)
                .orderByAsc(Blog::getCreateDt);
        return blogDao.selectOne(queryWrapper);
    }

    @Override
    public List<BlogHotDto> queryHotBlog(int top) {
        return blogDao.queryHotBlog(top);
    }

    @Override
    public void deleteById(String blogId) {
        blogDao.deleteById(blogId);
    }

    @Override
    public void updateBlogRead(String uid) {
        List<String> blogIds = (List<String>) httpSession.getAttribute("readBlogs");
        if (blogIds == null) {
            blogIds = new ArrayList<>();
            httpSession.setAttribute(Constant.READ_BLOG, blogIds);
        }
        if (!blogIds.contains(uid)) {
            blogDao.updateBlogRead(uid);
            blogIds.add(uid);
        }
    }

    @Override
    public void updateBlogGood(String uid) {
        List<String> blogIds = (List<String>) httpSession.getAttribute("goodBlogs");
        if (blogIds == null) {
            blogIds = new ArrayList<>();
            httpSession.setAttribute(Constant.GOOD_BLOG, blogIds);
        }
        if (!blogIds.contains(uid)) {
            blogDao.updateBlogGood(uid);
            blogIds.add(uid);
        }
    }
}
