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
        List<CatalogDto> noteList = noteService.queryAllCatalogDtoList();
        list.addAll(noteList);
        return buildCateTree(list);
    }

    @Override
    public List<Tree> loadNodesByDir(String dirId) {
        if (StringUtils.hasLength(dirId)) {
            // 查询当前目录节点
            List<CatalogDto> catList = catalogDao.queryList(dirId);
            // 查询当前 note 节点
            List<CatalogDto> noteList = noteService.queryCatalogDtoList(dirId);
            catList.addAll(noteList);
            return buildCateTree(catList);
        }
        return null;
    }

    @Override
    public void deleteByUid(String uid) {
        catalogDao.deleteById(uid);
    }

    private List<Tree> buildCateTree(List<CatalogDto> list) {
        return Tree.buildTree(list, "id", tree -> tree.get("dirId"), false);
    }
}
