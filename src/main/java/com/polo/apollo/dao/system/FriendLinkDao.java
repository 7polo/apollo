package com.polo.apollo.dao.system;

import com.polo.apollo.entity.modal.system.FriendLink;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
@Repository
public interface FriendLinkDao extends BaseMapper<FriendLink> {

    List<FriendLink> queryListBySize(int top);
}
