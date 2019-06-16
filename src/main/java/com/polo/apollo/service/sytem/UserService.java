package com.polo.apollo.service.sytem;

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


    void updatePassword(User user, String oldPass, String newPass);
}
