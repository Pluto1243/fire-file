package cn.raccoon.team.boot.service.rocket;

import cn.raccoon.team.boot.entity.FireFile;
import cn.raccoon.team.boot.entity.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(topic = "fire-topic", consumerGroup = "fireGroup")
public class RocketConsumer implements RocketMQListener<String> {

    @Autowired
    private RedisTemplate redisTemplate;


    @SneakyThrows
    @Override
    public void onMessage(String key) {
        // 消费延迟删除消息
        // 获取到key下的文件路径并且删除该文件
        Object file = redisTemplate.opsForValue().get(key);
        if (file != null) {
            log.info("销毁文件: 文件为： {}", file);
        }
    }
}
