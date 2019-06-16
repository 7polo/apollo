package com.polo.apollo.dao.blog;

import com.polo.apollo.entity.dto.BlogDto;
import com.polo.apollo.entity.dto.BlogHotDto;
import com.polo.apollo.entity.modal.blog.Blog;
import com.polo.apollo.entity.vo.BlogVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author baoqianyong
 */
@Repository
public interface BlogDao extends BaseMapper<Blog>{

    /**
     * 分页查询
     * @param page
     * @param blogVo 查询参数
     * @return
     */
    IPage<BlogDto> queryBlogPage(Page page, @Param("vo") BlogVo blogVo);


    BlogDto queryBlogById(String uid);

    List<BlogHotDto> queryHotBlog(int top);

    /**
     * 增加博客阅读
     * @param uid
     */
    void updateBlogRead(String uid);


    /**
     * 增加博客点赞数
     * @param uid
     */
    void updateBlogGood(String uid);
}
