package cn.raccoon.team.boot.service.rocket;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "testGroup")
public class RocketConsumer implements RocketMQListener<Message> {

    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        log.info("收到消息: topic = {}, body = {}", message.getTopic(), new String(message.getBody()));
    }
}
