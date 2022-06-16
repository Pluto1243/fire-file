package cn.raccoon.team.boot.service.rocket;

import cn.raccoon.team.boot.entity.FireFile;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * 负责消费延时消息，删除文件和key
 *
 * @author wangjie
 * @date 11:34 2022年06月16日
 **/
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
        log.info("延时消息收到key："+ key);
        Object json = redisTemplate.opsForValue().get(key);
        if (json != null) {
            // 获得阅后即焚文件
            FireFile fireFile = JSONObject.parseObject(json.toString(), FireFile.class);
            // 判断文件是否存在
            File file = new File(fireFile.getPath());
            log.info(file.getPath());
            if (file.exists()) {
                // 文件存在  删除文件
                Runtime runtime = Runtime.getRuntime();
                String command = "rm -rf " + file.getPath();
                runtime.exec(command);
                log.info(command);
            }
            // key失效
            redisTemplate.persist(key);
        }
    }
}
