package com.polo.apollo.service.sytem.impl;

import com.polo.apollo.common.EncryptUtil;
import com.polo.apollo.dao.system.UserDao;
import com.polo.apollo.entity.modal.system.User;
import com.polo.apollo.service.sytem.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByIdentify(String idInfo) {
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        // 邮箱 或 用户名
        query.and(i -> i.eq(User::getUsername, idInfo).or().eq(User::getEmail, idInfo));
        return userDao.selectOne(query);
    }

    @Override
    public void updatePassword(User user, String oldPass, String newPass) {
        if (EncryptUtil.isPasswordEqual(user, oldPass)) {
            EncryptUtil.encryptUser(user, newPass);
            userDao.updateById(user);
        }
        throw new RuntimeException("旧密码不正确");
    }
}
