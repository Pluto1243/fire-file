package cn.raccoon.team.boot.service;

import java.util.List;

/**
 * 消息队列服务
 *
 * @author wangjie
 * @date 17:49 2022年06月01日
 **/
public interface IRocketService {

    void sendInfo(String topicName, String info, Integer level);

    List<String> listTimeLevel();
}
