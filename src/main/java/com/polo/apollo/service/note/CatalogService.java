package com.polo.apollo.service.note;

import com.polo.apollo.entity.dto.Tree;
import com.polo.apollo.entity.modal.note.Catalog;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/05/24
 */
public interface CatalogService {

    void save(Catalog catalog);

    void deleteByUid(String uid);

    /**
     * 加载全部节点
     *
     * @return
     */
    List<Tree> loadTreeAll();

    /**
     * 根据父目录加载子节点
     * 目录节点 + note 节点
     *
     * @param dirId
     * @return
     */
    List<Tree> loadNodesByDir(String dirId);
}
