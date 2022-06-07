package cn.raccoon.team.boot.controller;

import cn.raccoon.team.boot.exception.response.R;
import cn.raccoon.team.boot.service.IRocketService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 阅后即焚控制类
 *
 * @author wangjie
 * @date 17:43 2022年06月07日
 **/
@Api(tags = "阅后即焚")
@RestController
@RequestMapping("fire")
public class FireController {

    @Autowired
    private IRocketService rocketService;

    @ApiOperation("时间精度")
    @ApiOperationSupport(order = 100)
    @GetMapping("/listTimeLevel")
    public R listTimeLevel() {
        return R.ok(rocketService.listTimeLevel());
    }
}
