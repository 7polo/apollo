package com.polo.apollo.dao.system;

import com.polo.apollo.entity.modal.system.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author baoqianyong
 * @date 2019/06/03
 */
@Repository
public interface UserDao extends BaseMapper<User> {
}
