package cn.raccoon.team.boot.service.impl;

import cn.raccoon.team.boot.entity.FireFile;
import cn.raccoon.team.boot.entity.KeyCode;
import cn.raccoon.team.boot.service.IFireFileService;
import cn.raccoon.team.boot.service.IRocketService;
import cn.raccoon.team.boot.utils.RandomUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class FireFileServiceImpl implements IFireFileService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IRocketService rocketService;

    @Value("${prefix}")
    private String prefix;

    private static final String TOPIC_NAME = "fire-topic";

    @Override
    public String sendFireFile(FireFile fireFile) {
        // 生成随机链接
        String key = RandomUtils.generateByRandom(8);
        fireFile.setKey(key);
        // 生成随机提取码
        String code = String.valueOf((int)(Math.floor((Math.random() * 9 + 1) * 1000)));
        fireFile.setCode(code);
        // 发送延时消息，通知删除
        if (fireFile.getExpire() && fireFile.getExpireLevel() != null) {
            rocketService.sendFire(TOPIC_NAME, fireFile.getKey(), fireFile.getExpireLevel());
            // 获取时间
            Map<Integer, Long> timeMap = rocketService.getTimeMap();
            // 将数据缓存到redis
            // TODO 后续需要做持久化
            redisTemplate.opsForValue().set(key, JSONObject.toJSONString(fireFile), timeMap.get(fireFile.getExpireLevel()), TimeUnit.SECONDS);
        } else {
            // 30天后过期
            redisTemplate.opsForValue().set(key, JSONObject.toJSONString(fireFile), 60 * 60 * 24 * 30, TimeUnit.SECONDS);
        }
        // 返回链接和提取码
        KeyCode keyCode = new KeyCode();
        keyCode.setKey(prefix + key);
        keyCode.setCode(code);
        return key;
    }
}
