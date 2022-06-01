package cn.raccoon.team.boot.service.impl;

import cn.raccoon.team.boot.entity.User;
import cn.raccoon.team.boot.exception.CommonException;
import cn.raccoon.team.boot.exception.EmError;
import cn.raccoon.team.boot.mapper.IUserMapper;
import cn.raccoon.team.boot.service.IUserService;
import cn.raccoon.team.boot.utils.BCryptPasswordEncoderUtil;
import cn.raccoon.team.boot.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Qian
 * @Date 2021/12/20 16:33
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper mapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private BCryptPasswordEncoderUtil passwordEncoderUtil;

    /**
     * 获取用户
     *
     * @param account
     * @return
     */
    @Override
    public User getUserByAccount(String account) {
        return mapper.getUserByAccount(account);
    }

    /**
     * 用户登录
     *
     * @param account
     * @param passwd
     * @return
     */
    @Override
    public User userLogin(String account, String passwd) {
        final User user = getUserByAccount(account);
        if (user == null) {
            throw new CommonException(EmError.USER_NOT_FOUND);
        }
        if (passwordEncoderUtil.matches(passwd, user.getPasswd())) {
            user.setToken(jwtTokenUtil.generateToken(user.getAccount()));
            return user;
        }
        throw new CommonException(EmError.PASSWD_ACCOUNT_ERROR);
    }

    /**
     * 生成密钥
     *
     * @param password
     * @return
     */
    @Override
    public String changePassword(String password) {
        return passwordEncoderUtil.encode(password);
    }


}
