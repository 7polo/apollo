package com.polo.apollo.service.note.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.polo.apollo.common.Constant;
import com.polo.apollo.common.entity.Tree;
import com.polo.apollo.common.util.Utils;
import com.polo.apollo.dao.note.CatalogDao;
import com.polo.apollo.dao.note.NoteDao;
import com.polo.apollo.entity.dto.CatalogDto;
import com.polo.apollo.entity.dto.NoteDto;
import com.polo.apollo.entity.modal.note.Note;
import com.polo.apollo.entity.modal.note.Tag;
import com.polo.apollo.entity.vo.NoteVo;
import com.polo.apollo.service.note.NoteService;
import com.polo.apollo.service.note.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author baoqianyong
 * @date 2019/05/25
 */
@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private CatalogDao catalogDao;

    @Autowired
    private TagService tagService;

    @Override
    public void save(NoteDto note) {

        if (StringUtils.hasLength(note.getUid()) && noteDao.selectById(note.getUid()) != null) {
            noteDao.updateById(note);
        } else {
            noteDao.insert(note);
        }
//
//        if (StringUtils.hasLength(note.getTags())) {
//            String[] tags = note.getTags().split(",");
//            Set<String> tagSet = new HashSet<>();
//            for (String tagName : tags) {
//                tagSet.add(tagName);
//            }
//            tagService.addNoteTag(tagSet, note.getUid());
//        }
    }

    @Override
    public void share(Note note) {
        note.setPublishDt(new Date());
        noteDao.updateById(note);
    }

    @Override
    public void deleteByUid(String uid) {
        noteDao.deleteById(uid);
    }

    @Override
    public NoteDto queryById(String uid) {
        NoteDto dto = new NoteDto();
        Note note = noteDao.selectById(uid);
        BeanUtils.copyProperties(note, dto);
        // 塞值 标签
        dto.setTags(tagService.queryByNoteId(uid));
        return dto;
    }

    @Override
    public IPage<NoteDto> queryPage(NoteVo vo, int start, int limit) {
        return noteDao.queryBlogPage(new Page<>(start, limit), vo);
    }

    @Override
    public List<CatalogDto> queryCatalogDtoList(String dirId) {
        if (StringUtils.hasLength(dirId)) {
            return noteDao.queryCatalogDtoList(dirId);
        }
        return new ArrayList<>();
    }

    @Override
    public Tree queryNoteTree(String uid) {
        CatalogDto dto = noteDao.queryCatalogDto(uid);
        if (dto != null) {

            Map<String, CatalogDto> map = new HashMap<>();
            for (CatalogDto cat : catalogDao.queryList(null)) {
                map.put(cat.getId(), cat);
            }
            List<CatalogDto> catList = new ArrayList<>();
            while (true) {
                catList.add(dto);
                dto = map.get(dto.getDirId());
                if (dto == null) {
                    break;
                }
                // 移除
                map.remove(dto.getId());
                if ("root".equals(dto.getDirId())) {
                    catList.add(dto);
                    break;
                }
            }
            List<Tree> treeList = Utils.buildTree(catList, (c1, c2) -> c1.get("id").equals(c2.get("dirId")));
            return treeList.size() > 0 ? treeList.get(0) : null;
        }
        return null;
    }

    @Override
    public void addRead(String uid) {
        List<String> blogIds = (List<String>) httpSession.getAttribute(Constant.READ_BLOG);
        if (blogIds == null) {
            blogIds = new ArrayList<>();
            httpSession.setAttribute(Constant.READ_BLOG, blogIds);
        }
        if (!blogIds.contains(uid)) {
            noteDao.addRead(uid);
            blogIds.add(uid);
        }
    }

    @Override
    public void addGood(String uid) {
        List<String> blogIds = (List<String>) httpSession.getAttribute(Constant.GOOD_BLOG);
        if (blogIds == null) {
            blogIds = new ArrayList<>();
            httpSession.setAttribute(Constant.GOOD_BLOG, blogIds);
        }
        if (!blogIds.contains(uid)) {
            noteDao.addGood(uid);
            blogIds.add(uid);
        }
    }

    @Override
    public Note queryPublishedById(String uid) {
        LambdaQueryWrapper<Note> query = new LambdaQueryWrapper<>();
        query.eq(Note::getUid, uid).isNotNull(Note::getPublishDt);
        return noteDao.selectOne(query);
    }

    @Override
    public List<NoteDto> queryPublishedPreAndNext(Note note) {
        return noteDao.queryPublishedPreAndNext(note.getUid(), note.getPublishDt());
    }

    @Override
    public List<Note> queryHotBlog(int i) {
        return noteDao.queryHotBlog(i);
    }
}
