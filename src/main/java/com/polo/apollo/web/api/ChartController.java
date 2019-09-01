package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author baoqianyong
 * @date 2019/9/1
 */
@RestController
@RequestMapping("chart")
public class ChartController {

    @GetMapping("visiteCount")
    public Result queryVisteCount() {

        return Result.success();
    }

    @GetMapping("blogCount")
    public Result queryBlogCount() {
        return Result.success();
    }
}
