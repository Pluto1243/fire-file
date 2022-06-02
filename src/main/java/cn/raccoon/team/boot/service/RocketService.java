package cn.raccoon.team.boot.service;

/**
 * 消息队列服务
 *
 * @author wangjie
 * @date 17:49 2022年06月01日
 **/
public interface RocketService {

    void sendInfo(String topicName, String info);

}
