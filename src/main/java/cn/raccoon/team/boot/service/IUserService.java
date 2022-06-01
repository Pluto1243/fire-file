package cn.raccoon.team.boot.service;

import cn.raccoon.team.boot.entity.User;

/**
 * @Author Qian
 * @Date 2021/12/20 16:32
 */
public interface IUserService {
    /**
     * 获取用户
     *
     * @param account
     * @return
     */
    User getUserByAccount(String account);

    /**
     * 用户登录
     *
     * @param account
     * @param passwd
     * @return
     */
    User userLogin(String account, String passwd);


    /**
     * 生成密钥
     *
     * @param password
     * @return
     */
    String changePassword(String password);
}
