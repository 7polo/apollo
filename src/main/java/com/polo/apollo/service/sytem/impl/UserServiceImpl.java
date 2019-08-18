package com.polo.apollo.service.sytem.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.polo.apollo.Application;
import com.polo.apollo.common.util.EncryptUtil;
import com.polo.apollo.dao.system.UserDao;
import com.polo.apollo.entity.dto.UserDto;
import com.polo.apollo.entity.modal.system.User;
import com.polo.apollo.service.sytem.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;

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

    @Override
    public void save(User user) {
        userDao.updateById(user);
        this.getAuthor(true);
    }

    @Override
    public User queryById(String uid) {
        return userDao.selectById(uid);
    }

    @Override
    public synchronized UserDto getAuthor(boolean load) {
        if (Application.user == null || load) {
            User user = userDao.selectOne(null);
            if (user != null) {
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(user, userDto, "skills", "password");
                if (StringUtils.hasLength(user.getSkills())) {
                    String[] skills = user.getSkills().split(",");
                    for (String skill : skills) {
                        userDto.getSkills().add(skill.trim());
                    }
                }
                Application.user = userDto;
            }
        }
        return Application.user;
    }
}
