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

    /**
     * 查询tag 和 对应的count
     * @return
     */
    List<TagDto> queryList();

    void deleteByUid(String uid);

    void addNoteTag(Set<String> tags, String noteId);

    List<Tag> queryByNoteId(String uid);

    List<TagDto> queryByNoteIds(List<String> notdIds);

    /**
     * 查询博客对应的标签个数
     * @return
     */
    List<TagDto> queryBlogCount();
}
