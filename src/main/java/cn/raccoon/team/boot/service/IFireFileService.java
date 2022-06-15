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

}
