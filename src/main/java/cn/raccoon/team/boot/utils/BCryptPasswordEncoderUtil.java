package cn.raccoon.team.boot.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Qian
 */
@Component
public class BCryptPasswordEncoderUtil extends BCryptPasswordEncoder {

    /**
     * 加密
     *
     * @param rawPassword  真实密码
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword);
    }

    /**
     * 对比密码
     *
     * @param rawPassword  真实密码
     * @param encodedPassword  加密密文
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        return super.matches(rawPassword,encodedPassword);
    }

}
