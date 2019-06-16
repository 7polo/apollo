package com.polo.apollo.service.sytem;

import com.polo.apollo.entity.modal.system.DataDic;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/06/16
 */
public interface DataDicService {

    /**
     * 更新或者保存 数据字典
     * @param dataDic
     */
    void saveOrUpdate(DataDic dataDic);

    /**
     * 根据类型查询数据字典
     * @param type
     * @return
     */
    List<DataDic> queryListByType(String type);
}
