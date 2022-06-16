package cn.raccoon.team.boot.service;

import cn.raccoon.team.boot.entity.FireFile;
import cn.raccoon.team.boot.entity.KeyCode;

import javax.servlet.http.HttpServletResponse;

public interface IFireFileService {

    /**
     * @description 发送阅后即焚
     *
     * @author wangjie
     * @date 11:34 2022年06月16日
     * @param fireFile
     * @return cn.raccoon.team.boot.entity.KeyCode
     */
    KeyCode sendFireFile(FireFile fireFile);

    /**
     * @description 验证链接是否有效
     *
     * @author wangjie
     * @date 18:30 2022年06月15日
     * @param key
     * @return cn.raccoon.team.boot.entity.FireFile
     */
    FireFile getFileInfoByKey(String key);

    /**
     * @description 提取文件
     *
     * @author wangjie
     * @date 18:31 2022年06月15日
     * @param key
     * @param code
     * @param response
     * @return
     */
    void extractFile(String key, String code, HttpServletResponse response);
}
