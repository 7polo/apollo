package com.polo.apollo.service.sytem.impl;

import com.polo.apollo.dao.system.DataDicDao;
import com.polo.apollo.entity.modal.system.DataDic;
import com.polo.apollo.service.sytem.DataDicService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/06/16
 */
@Service
public class DataDicServiceImpl implements DataDicService {

    @Autowired
    private DataDicDao dataDicDao;

    @Override
    public void saveOrUpdate(DataDic dataDic) {
        if (dataDicDao.updateById(dataDic) == 0) {
            dataDicDao.insert(dataDic);
        }
    }

    @Override
    public List<DataDic> queryListByType(String type) {
        LambdaQueryWrapper<DataDic> query = new LambdaQueryWrapper<>();
        query.eq(DataDic::getType, type);
        return dataDicDao.selectList(query);
    }
}
