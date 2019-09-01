package com.polo.apollo.service.sytem;

import com.polo.apollo.entity.modal.system.SysConfig;

/**
 * @author baoqianyong
 * @date 2019/06/12
 */
public interface SysConfigService {

    /**
     * 获取系统参数
     *
     * @return
     */
    SysConfig getSysConfig();

    /**
     * 保存 系统配置
     *
     * @param vo
     * @return
     */
    SysConfig updateConfig(SysConfig vo);
}
