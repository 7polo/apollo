package com.polo.apollo.service.sytem.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.polo.apollo.common.Constant;
import com.polo.apollo.dao.system.SysConfigDao;
import com.polo.apollo.entity.modal.system.Seo;
import com.polo.apollo.entity.modal.system.SysConfig;
import com.polo.apollo.service.sytem.SeoService;
import com.polo.apollo.service.sytem.SysConfigService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author baoqianyong
 * @date 2019/06/12
 */
@Service
@Log
public class SysConfigServiceImpl implements SysConfigService {

    public final String CACHE_KEY = "'system_config'";

    @Autowired
    private SysConfigDao sysConfigDao;

    @Autowired
    private SeoService seoService;

    @Override
    @Cacheable(value = Constant.CACHE_SYSTEM, key = CACHE_KEY)
    public SysConfig getSysConfig() {
        SysConfig sysConfig = sysConfigDao.selectOne(new LambdaQueryWrapper<>());
        if (sysConfig == null) {
            sysConfig = new SysConfig();
            sysConfig.setTitle("");
            sysConfigDao.insert(sysConfig);
        }
        // 查询 seo
        Seo seo = seoService.querySeoByRelateId(Constant.SEO_SITE, sysConfig.getUid());
        if (seo != null) {
            sysConfig.setKeyword(seo.getKeyword());
            sysConfig.setDescription(seo.getDescription());
        }
        log.info("加载系统参数......success");
        return sysConfig;
    }

    @Override
    @CachePut(value = Constant.CACHE_SYSTEM, key = CACHE_KEY)
    public SysConfig updateConfig(SysConfig vo) {
        // 保证只有一个
        sysConfigDao.updateById(vo);
        Seo seo = seoService.querySeoByRelateId(Constant.SEO_SITE, vo.getUid());
        if (seo == null) {
            seo = new Seo();
            seo.setRelateId(vo.getUid());
        }
        seo.setKeyword(vo.getKeyword());
        seo.setDescription(vo.getDescription());
        seoService.saveOrUpdateSeo(seo, Constant.SEO_SITE);
        return vo;
    }
}
