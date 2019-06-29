package com.polo.apollo.dao.note;

import com.polo.apollo.entity.dto.TagDto;
import com.polo.apollo.entity.modal.note.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
@Repository
public interface TagDao extends BaseMapper<Tag> {

    /**
     * name in
     * @param tags
     * @return
     */
    List<Tag> queryInNames(@Param("tags") Set<String> tags);

    List<Tag> queryByNoteId(String noteId);

    /**
     * 查询标签个数
     * @param published
     * 1. null 全部
     * 2. false 未发布
     * 2. true 发布
     * @return
     */
    List<TagDto> queryTagCount(@Param("published") Boolean published);

    /**
     * 查询
     * @param notdIds
     * @return
     */
    List<TagDto> queryByNoteIds(@Param("notdIds") List<String> notdIds);
}
