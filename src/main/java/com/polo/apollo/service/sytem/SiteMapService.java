package com.polo.apollo.service.sytem;

import com.polo.apollo.entity.dto.SiteMapDto;
import com.polo.apollo.entity.modal.system.SiteMap;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/06/23
 */
public interface SiteMapService {

    /**
     * 创建 xml
     * @param list
     * @return
     */
    String buildXml(List<SiteMapDto> list);

    SiteMap getSiteMap();
}
