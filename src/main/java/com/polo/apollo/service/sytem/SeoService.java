package com.polo.apollo.service.sytem;

import com.polo.apollo.entity.modal.system.Seo;

/**
 * @author baoqianyong
 * @date 2019/06/14
 */
public interface SeoService {

    /**
     * 更新seo
     *
     * @param seo
     * @param type
     */
    void saveOrUpdateSeo(Seo seo, String type);

    /**
     * 查询 seo
     *
     * @param type     类型
     * @param relateId 关联id
     * @return
     */
    Seo querySeoByRelateId(String type, String relateId);
}
