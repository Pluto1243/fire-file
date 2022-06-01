package cn.raccoon.team.boot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Qian
 * @Date 2021/12/20 16:32
 */
@Data
public class User implements Serializable {
    private Integer id;
    private String account;
    private String passwd;
    private String realName;
    private String token;
}
