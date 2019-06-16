package com.polo.apollo.service.sytem.impl;

import com.polo.apollo.dao.system.FriendLinkDao;
import com.polo.apollo.entity.modal.system.FriendLink;
import com.polo.apollo.service.sytem.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
@Service
public class FriendLinkServiceImpl implements FriendLinkService {

    @Autowired
    private FriendLinkDao friendLinkDao;

    @Override
    public void update(FriendLink friendLink) {
        if (StringUtils.hasLength(friendLink.getUid())) {
            friendLinkDao.updateById(friendLink);
        } else {
            friendLinkDao.insert(friendLink);
        }
    }

    @Override
    public List<FriendLink> queryListBySize(int top) {
        return friendLinkDao.queryListBySize(top);
    }
}
