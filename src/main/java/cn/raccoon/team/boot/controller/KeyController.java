package cn.raccoon.team.boot.controller;

import cn.raccoon.team.boot.exception.response.R;
import cn.raccoon.team.boot.service.IFireFileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "key")
@RestController
@RequestMapping("/s")
public class KeyController {

    @Autowired
    private IFireFileService fireFileService;

    @GetMapping("/{key}")
    public R getFireFileWithKey(@PathVariable("key") String key) {
        return R.ok(fireFileService.getFileInfoByKey(key));
    }
}
