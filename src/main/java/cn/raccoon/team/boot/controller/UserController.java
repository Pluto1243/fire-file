package cn.raccoon.team.boot.controller;

import cn.raccoon.team.boot.exception.response.R;
import cn.raccoon.team.boot.service.IUserService;
import cn.raccoon.team.boot.service.RocketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Qian
 * @Date 2021/12/20 16:33
 */
@RestController
@Api(tags = "用户")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private RocketService rocketService;

    @PostMapping("/userLogin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", dataTypeClass = String.class),
            @ApiImplicitParam(name = "passwd", value = "密码", dataTypeClass = String.class)
    })
    @ApiOperation(value = "用户登录")
    public R userLogin(@RequestParam("account") String account,
                       @RequestParam("passwd") String passwd) {
        return R.ok(userService.userLogin(account, passwd));
    }

    @PostMapping("/changePassword")
    @ApiImplicitParam(name = "password", value = "密码", dataTypeClass = String.class)
    @ApiOperation(value = "生成密钥")
    public R changePassword(@RequestParam("password") String password){
        return R.ok(userService.changePassword(password));
    }

    @GetMapping("/sendMq")
    public R sendMq(@RequestParam("topicName")String topicName,
                    @RequestParam("info")String info) {
        rocketService.sendInfo(topicName, info);
        return R.ok();
    }

}
