package com.polo.apollo.service.note.impl;

import com.polo.apollo.common.Constant;
import com.polo.apollo.common.util.Utils;
import com.polo.apollo.dao.note.TagDao;
import com.polo.apollo.dao.note.TagNoteDao;
import com.polo.apollo.entity.dto.TagDto;
import com.polo.apollo.entity.modal.note.Tag;
import com.polo.apollo.entity.modal.note.TagNote;
import com.polo.apollo.service.note.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private TagNoteDao tagNoteDao;

    @Override
    public void save(Tag tag) {
        tag.setName(tag.getName().trim());
        if (StringUtils.hasLength(tag.getUid())) {
            tagDao.updateById(tag);
        } else {
            tagDao.insert(tag);
        }
    }

    @Override
    public void deleteByUid(String uid) {
        tagDao.deleteById(uid);
    }

    @Override
    public void addNoteTag(Set<String> tags, String noteId) {
        tagNoteDao.deleteByNoteId(noteId);

        if (Utils.isEmpty(tags)) {
            return;
        }
        // in 查询 数据库中已存在的
        List<Tag> dbTags = tagDao.queryInNames(tags);
        for (String tagName : tags) {
            boolean related = false;
            for (Tag dbtag : dbTags) {
                if (tagName.equalsIgnoreCase(dbtag.getName())) {
                    // 建立关系
                    TagNote tagNote = new TagNote();
                    tagNote.setTagId(dbtag.getUid());
                    tagNote.setNoteId(noteId);
                    tagNoteDao.insert(tagNote);

                    related = true;
                    break;
                }
            }

            if (!related) {
                Tag tag = new Tag();
                tag.setName(tagName);
                tagDao.insert(tag);

                TagNote tagNote = new TagNote();
                tagNote.setTagId(tag.getUid());
                tagNote.setNoteId(noteId);

                tagNoteDao.insert(tagNote);
            }
        }
    }

    @Override
    public List<Tag> queryByNoteId(String noteId) {
        return tagDao.queryByNoteId(noteId);
    }

    @Override
    public List<TagDto> queryByNoteIds(List<String> notdIds) {
        if (Utils.isEmpty(notdIds)) {
            return new ArrayList<>();
        }
        return tagDao.queryByNoteIds(notdIds);
    }

    @Override
    @Cacheable(value = Constant.CACHE_BLOG, key = "'tags'")
    public List<TagDto> queryTagCount(Boolean published) {
        return tagDao.queryTagCount(published);
    }
}
