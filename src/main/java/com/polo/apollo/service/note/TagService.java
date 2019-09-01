package com.polo.apollo.service.note;

import com.polo.apollo.entity.dto.TagDto;
import com.polo.apollo.entity.modal.note.Tag;

import java.util.List;
import java.util.Set;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
public interface TagService {

    void save(Tag tag);

    void deleteByUid(String uid);

    void addNoteTag(Set<String> tags, String noteId);

    List<Tag> queryByNoteId(String uid);

    List<TagDto> queryByNoteIds(List<String> notdIds);

    /**
     * 查询标签个数
     *
     * @param published 1. null 全部
     *                  2. false 未发布
     *                  2. true 发布
     * @return
     */
    List<TagDto> queryTagCount(Boolean published);
}
