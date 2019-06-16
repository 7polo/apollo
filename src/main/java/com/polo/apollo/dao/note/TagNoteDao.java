package com.polo.apollo.dao.note;

import com.polo.apollo.entity.modal.note.TagNote;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
@Repository
public interface TagNoteDao extends BaseMapper<TagNote> {

    void deleteByNoteId(String noteId);
}
