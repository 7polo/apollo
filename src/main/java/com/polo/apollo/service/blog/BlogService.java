package com.polo.apollo.service.blog;

import com.polo.apollo.entity.dto.BlogDto;
import com.polo.apollo.entity.dto.BlogHotDto;
import com.polo.apollo.entity.modal.blog.Blog;
import com.polo.apollo.entity.modal.note.Note;
import com.polo.apollo.entity.vo.BlogVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author baoqianyong
 */
public interface BlogService {

    /**
     * 保存blog
     * 如果 blog uid 已存在， 则更新， 否则插入
     *
     * @param blog
     */
    void saveBlog(Blog blog);

    /**
     * 根据id查询
     *
     * @param uid
     * @return
     */
    BlogDto queryById(String uid);

    /**
     * 分页查询
     *
     * @param blogVo
     * @param start
     * @param limit
     * @return
     */
    Page<BlogDto> queryPage(BlogVo blogVo, int start, int limit);

    /**
     * 根据Note创建blog
     *
     * @param note
     * @return
     */
    Blog createBlogWithNote(Note note);

    Blog queryBlogNoteId(String noteId);

    /**
     * 查询热门文章
     *
     * @param top top
     * @return
     */
    List<BlogHotDto> queryHotBlog(int top);

    /**
     * 删除
     *
     * @param blogId
     */
    void deleteById(String blogId);


    /**
     * 增加博客阅读数
     *
     * @param uid
     */
    void updateBlogRead(String uid);

    void updateBlogGood(String uid);
}
