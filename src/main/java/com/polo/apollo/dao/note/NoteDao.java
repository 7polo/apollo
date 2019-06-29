package com.polo.apollo.dao.note;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polo.apollo.entity.dto.CatalogDto;
import com.polo.apollo.entity.dto.NoteDto;
import com.polo.apollo.entity.modal.note.Note;
import com.polo.apollo.entity.vo.NoteVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/05/19
 */
@Repository
public interface NoteDao extends BaseMapper<Note> {

    /**
     * 分页查询
     *
     * @param objectPage
     * @param vo
     * @return
     */
    IPage<NoteDto> queryBlogPage(Page<Object> objectPage, @Param("vo") NoteVo vo);


    /**
     * 查询CatalogDto list
     *
     * @param dirId 如果存在则，查询该目录下的note ，否则查询全部
     * @return
     */
    List<CatalogDto> queryCatalogDtoList(@Param("dirId") String dirId);

    /**
     * 查询 CatalogDto 形式的 note
     *
     * @param uid
     * @return
     */
    CatalogDto queryCatalogDto(@Param("uid") String uid);

    /**
     * 增加阅读量
     *
     * @param uid
     */
    void addRead(String uid);

    /**
     * 点赞
     *
     * @param uid
     */
    void addGood(String uid);

    /**
     * 查询上下篇博客
     *
     * @param uid
     * @param publishDt
     * @return
     */
    List<NoteDto> queryPublishedPreAndNext(@Param("uid") String uid, @Param("publishDt") Date publishDt);

    /**
     * 查询热门博客
     * @param limit 条数
     * @return
     */
    List<Note> queryHotBlog(int limit);
}
