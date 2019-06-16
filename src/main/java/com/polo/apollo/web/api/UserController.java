package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author baoqianyong
 * @date 2019/06/03
 */
@RestController
@RequestMapping("user")
public class UserController {


    @RequestMapping("updatePassword")
    public Result updatePassword(String oldPassword, String newPassword) {
        return Result.success();
    }
}
