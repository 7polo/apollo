package com.polo.apollo.service.note;

import com.polo.apollo.entity.dto.CatalogDto;
import com.polo.apollo.entity.dto.NoteDto;
import com.polo.apollo.common.entity.Tree;
import com.polo.apollo.entity.modal.note.Note;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/05/19
 */
public interface NoteService {

    void save(NoteDto note);

    /**
     * 分享博客
     * @param note
     */
    void share(Note note);

    void deleteByUid(String uid);

    NoteDto queryById(String uid);

    /**
     * 查询分页
     * @param noteDto
     * @param start
     * @param limit
     * @return
     */
    IPage<Note> queryPage(NoteDto noteDto, int start, int limit);

    /**
     * 查询 目录下的 note
     * @param dirId
     * @return
     */
    List<CatalogDto> queryDtoList(String dirId);

    /**
     * 查询 note 的目录树
     * @param uid
     * @return
     */
    Tree queryNoteTree(String uid);

    /**
     * 增加博客阅读数
     *
     * @param uid
     */
    void updateRead(String uid);

    /**
     * 增加点赞数
     * @param uid
     */
    void updateGood(String uid);

    Note queryPublishedById(String uid);

    /**
     * 查询发布时间上下篇
     * @param note
     * @return
     */
    List<Note> queryPublishedPreAndNext(Note note);
}
