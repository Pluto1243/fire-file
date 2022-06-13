package cn.raccoon.team.boot.controller;

import cn.raccoon.team.boot.entity.FireFile;
import cn.raccoon.team.boot.exception.response.R;
import cn.raccoon.team.boot.service.IFireFileService;
import cn.raccoon.team.boot.service.IRocketService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 阅后即焚控制类
 *
 * @author wangjie
 * @date 17:43 2022年06月07日
 **/
@Api(tags = "阅后即焚")
@RestController
@RequestMapping("fire")
@Validated
public class FireController {

    @Autowired
    private IRocketService rocketService;

    @Autowired
    private IFireFileService fireFileService;

    @ApiOperation("时间精度")
    @ApiOperationSupport(order = 100)
    @GetMapping("/listTimeLevel")
    public R listTimeLevel() {
        return R.ok(rocketService.listTimeLevel());
    }

    @ApiOperation("阅后即焚")
    @ApiOperationSupport(order = 101)
    @PostMapping("/sendFireFile")
    public R sendFireFile(@Valid FireFile fireFile) {
        return R.ok(fireFileService.sendFireFile(fireFile));
    }
}
