package com.polo.apollo.service.sytem.impl;

import com.polo.apollo.dao.system.SeoDao;
import com.polo.apollo.entity.modal.system.Seo;
import com.polo.apollo.service.sytem.SeoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/06/14
 */
@Service
public class SeoServiceImpl implements SeoService {

    @Autowired
    private SeoDao seoDao;

    @Override
    public void saveOrUpdateSeo(Seo seo, String type) {
        seo.setType(type);
        int result = seoDao.updateById(seo);
        if (result == 0) {
            seoDao.insert(seo);
        }
    }

    @Override
    public Seo querySeoByRelateId(String type, String relateId) {
        LambdaQueryWrapper<Seo> query = new LambdaQueryWrapper<>();
        query.eq(Seo::getRelateId, relateId).eq(Seo::getType, type);
        List<Seo> list = seoDao.selectList(query);
        return list.size() >= 1 ? list.get(0) : null;
    }
}
