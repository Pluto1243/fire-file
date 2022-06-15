package cn.raccoon.team.boot.service;

import cn.raccoon.team.boot.entity.FireFile;

public interface IFireFileService {

    /**
     * @description 发送阅后即焚
     *
     * @author wangjie
     * @date 14:14 2022年06月15日
     * @param fireFile
     * @return java.lang.String 
     */
    String sendFireFile(FireFile fireFile);

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
     * @return
     */
    void extractFile(String key, String code);
}
