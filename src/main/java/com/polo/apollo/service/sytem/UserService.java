package com.polo.apollo.service.sytem;

import com.polo.apollo.entity.dto.UserDto;
import com.polo.apollo.entity.modal.system.User;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
public interface UserService {

    /**
     * 根据用户关键信息查询用户
     * @param idInfo
     * @return
     */
    User getUserByIdentify(String idInfo);

    /**
     * 更新密码
     * @param user
     * @param oldPass
     * @param newPass
     */
    void updatePassword(User user, String oldPass, String newPass);

    /**
     * 更新
     * @param user
     */
    void save(User user);

    /**
     * 根据id 查询用户
     * @param uid
     * @return
     */
    User queryById(String uid);

    /**
     * 获取作者
     * @param load 是否从数据库加载
     * @return
     */
    UserDto getAuthor(boolean load);
}
