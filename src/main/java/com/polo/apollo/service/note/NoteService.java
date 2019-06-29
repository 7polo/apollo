package com.polo.apollo.service.note;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.polo.apollo.common.entity.Tree;
import com.polo.apollo.entity.dto.CatalogDto;
import com.polo.apollo.entity.dto.NoteDto;
import com.polo.apollo.entity.modal.note.Note;
import com.polo.apollo.entity.vo.NoteVo;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/05/19
 */
public interface NoteService {

    void save(NoteDto note);

    /**
     * 分享博客
     *
     * @param note
     */
    void share(Note note);

    void deleteByUid(String uid);

    /**
     * 根据id获取note
     *
     * @param uid
     * @return
     */
    NoteDto queryById(String uid);

    /**
     * 查询分页
     *
     * @param vo    查询参数
     * @param start
     * @param limit
     * @return
     */
    IPage<NoteDto> queryPage(NoteVo vo, int start, int limit);

    /**
     * 查询 目录下的 note
     *
     * @param dirId
     * @return
     */
    List<CatalogDto> queryCatalogDtoList(String dirId);

    /**
     * 查询 note 的目录树
     *
     * @param uid
     * @return
     */
    Tree queryNoteTree(String uid);

    /**
     * 增加博客阅读数
     *
     * @param uid
     */
    void addRead(String uid);

    /**
     * 增加点赞数
     *
     * @param uid
     */
    void addGood(String uid);

    /**
     * 查询发布的note
     *
     * @param uid
     * @return
     */
    Note queryPublishedById(String uid);

    /**
     * 查询发布时间上下篇
     *
     * @param note
     * @return
     */
    List<NoteDto> queryPublishedPreAndNext(Note note);
}
