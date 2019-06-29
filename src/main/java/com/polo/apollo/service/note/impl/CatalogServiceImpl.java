package com.polo.apollo.service.note.impl;

import com.polo.apollo.common.util.Utils;
import com.polo.apollo.dao.note.CatalogDao;
import com.polo.apollo.entity.dto.CatalogDto;
import com.polo.apollo.common.entity.Tree;
import com.polo.apollo.entity.modal.note.Catalog;
import com.polo.apollo.service.note.CatalogService;
import com.polo.apollo.service.note.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/05/24
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogDao catalogDao;

    @Autowired
    private NoteService noteService;

    @Override
    public void save(Catalog catalog) {
        if (StringUtils.hasLength(catalog.getUid()) && catalogDao.selectById(catalog.getUid()) != null) {
            catalogDao.updateById(catalog);
        } else {
            catalogDao.insert(catalog);
        }
    }

    @Override
    public List<Tree> loadTreeAll() {
        List<CatalogDto> list = catalogDao.queryList(null);
        return Utils.buildTree(list, (c1, c2) -> c1.get("id").equals(c2.get("dirId")));
    }

    @Override
    public List<Tree> loadNodesByDir(String dirId) {
        if (StringUtils.hasLength(dirId)) {
            // 查询当前目录节点
            List<CatalogDto> catList = catalogDao.queryList(dirId);
            // 查询当前 note 节点
            List<CatalogDto> noteList = noteService.queryCatalogDtoList(dirId);

            catList.addAll(noteList);
            return Utils.buildTree(catList, (c1, c2) -> c1.get("id").equals(c2.get("dirId")));
        }
        return null;
    }

    @Override
    public void deleteByUid(String uid) {
        catalogDao.deleteById(uid);
    }
}
