package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.modal.system.SysConfig;
import com.polo.apollo.entity.modal.system.User;
import com.polo.apollo.service.sytem.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author baoqianyong
 * @date 2019/06/03
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("updatePassword")
    public Result updatePassword(String oldPass, String newPass) {
        try {
            String uid = SecurityUtils.getSubject().getPrincipal().toString();
            userService.updatePassword(uid, oldPass, newPass);
            return Result.success();
        }catch (Exception e){

        }
        return Result.error();
    }

    @ApiOperation(value = "保存 用户信息")
    @PostMapping("save")
    public Result save(User user) {
        userService.save(user);
        return Result.success();
    }
}
