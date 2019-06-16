package com.polo.apollo.service.note;

import com.polo.apollo.entity.dto.CatalogDto;
import com.polo.apollo.entity.dto.NoteDto;
import com.polo.apollo.entity.dto.Tree;
import com.polo.apollo.entity.modal.note.Note;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/05/19
 */
public interface NoteService {

    void save(NoteDto note);

    void deleteByUid(String uid);

    NoteDto queryById(String uid);


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
}
