package com.polo.apollo.dao.note;

import com.polo.apollo.entity.dto.CatalogDto;
import com.polo.apollo.entity.modal.note.Catalog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/05/19
 */
@Repository
public interface CatalogDao extends BaseMapper<Catalog> {

    /**
     * 查询目录
     * @param dirId 直接父目录id， 如果不存在则查询所有的目录
     * @return
     */
    List<CatalogDto> queryList(@Param("dirId") String dirId);
}
