package com.polo.apollo.shiro;

import com.polo.apollo.entity.modal.system.User;
import com.polo.apollo.service.sytem.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
public class UserRealm extends AuthenticatingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String name = authenticationToken.getPrincipal().toString();
        User user = userService.getUserByIdentify(name);
        if (user == null) {
            throw new AuthenticationException("账号不存在");
        }
        //盐值
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        return new SimpleAuthenticationInfo(user.getUid(), user.getPassword(), salt, getName());
    }
}
