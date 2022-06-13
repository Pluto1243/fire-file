package cn.raccoon.team.boot.controller;

import cn.raccoon.team.boot.exception.response.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Api(tags = "key")
@RestController
@RequestMapping("/s")
public class KeyController {

    @GetMapping("/{key}")
    public R getFireFileWithKey(@PathVariable("key") String key) {
        return R.ok(key);
    }
}
