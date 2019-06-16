package com.polo.apollo.service.sytem;

import com.polo.apollo.entity.modal.system.FriendLink;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
public interface FriendLinkService {
    void update(FriendLink friendLink);

    List<FriendLink> queryListBySize(int top);
}
