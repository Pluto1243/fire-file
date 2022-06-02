package cn.raccoon.team.boot.service.rocket;

import cn.raccoon.team.boot.service.RocketService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RocketServiceImpl implements RocketService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void sendInfo(String topicName, String info) {
        // 发送消息
        rocketMQTemplate.convertAndSend(topicName, info);
    }
}
