package com.polo.apollo.service.sytem.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.polo.apollo.common.Constant;
import com.polo.apollo.common.util.EncryptUtil;
import com.polo.apollo.dao.system.UserDao;
import com.polo.apollo.entity.dto.UserDto;
import com.polo.apollo.entity.modal.system.User;
import com.polo.apollo.service.sytem.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String CACHE_KEY = "'user'";

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
    @CacheEvict(value = Constant.CACHE_SYSTEM, key = CACHE_KEY)
    public void updatePassword(String uid, String oldPass, String newPass) {
        User user = queryById(uid);
        if (user != null && EncryptUtil.isPasswordEqual(user, oldPass)) {
            EncryptUtil.encryptUser(user, newPass);
            userDao.updateById(user);
            return;
        }
        throw new RuntimeException("旧密码不正确");
    }

    @Override
    @CacheEvict(value = Constant.CACHE_SYSTEM, key = CACHE_KEY)
    public void save(User user) {
        userDao.updateById(user);
    }

    @Override
    public User queryById(String uid) {
        return userDao.selectById(uid);
    }

    @Override
    @Cacheable(value = Constant.CACHE_SYSTEM, key = CACHE_KEY)
    public UserDto getAuthor() {
        UserDto userDto = new UserDto();
        User user = userDao.selectOne(null);
        if (user != null) {
            BeanUtils.copyProperties(user, userDto, "skills", "password");
            if (StringUtils.hasLength(user.getSkills())) {
                String[] skills = user.getSkills().split(",");
                for (String skill : skills) {
                    userDto.getSkills().add(skill.trim());
                }
            }
        }
        return userDto;
    }
}
