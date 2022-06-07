package cn.raccoon.team.boot.service.rocket;

import cn.raccoon.team.boot.entity.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "testGroup")
public class RocketConsumer implements RocketMQListener<User> {

    @SneakyThrows
    @Override
    public void onMessage(User user) {
        log.info("收到消息: topic = {}", user.getToken());
    }
}
