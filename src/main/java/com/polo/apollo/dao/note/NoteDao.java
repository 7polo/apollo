package com.polo.apollo.dao.note;

import com.polo.apollo.entity.dto.CatalogDto;
import com.polo.apollo.entity.dto.NoteDto;
import com.polo.apollo.entity.modal.note.Note;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    IPage<Note> queryBlogPage(Page<Object> objectPage, NoteDto noteDto);

    /**
     * 查询list
     * @param dirId 如果存在则，查询该目录下的note ，否则查询全部
     * @return
     */
    List<CatalogDto> queryDtoList(@Param("dirId") String dirId);

    /**
     * 查询 dto 形式的 note
     * @param uid
     * @return
     */
    CatalogDto queryDto(@Param("uid") String uid);

    void updateRead(String uid);

    void updateGood(String uid);

    /**
     * 查询上下篇博客
     * @param uid
     * @param publishDt
     * @return
     */
    List<Note> queryPublishedPreAndNext(String uid, Date publishDt);
}
