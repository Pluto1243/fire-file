package cn.raccoon.team.boot.service;

import cn.raccoon.team.boot.entity.FireFile;

public interface IFireFileService {

    /**
     * @description 发送阅后即焚
     *
     * @author wangjie
     * @date 17:41 2022年06月13日 
     * @param fireFile
     * @return java.lang.String 
     */
    String sendFireFile(FireFile fireFile);

}
