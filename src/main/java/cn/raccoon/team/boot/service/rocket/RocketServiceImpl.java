package cn.raccoon.team.boot.service.rocket;

import cn.raccoon.team.boot.service.IRocketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class RocketServiceImpl implements IRocketService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.time-level}")
    private String times;

    @Override
    public void sendFire(String topicName, String key, Integer level) {
        // 发送同步延迟消息 0代表立即发送
        // 时间精度 [1m 5m 10m 20m 30m 45m 1h 2h 3h 6h 12h 1d 2d 3d 4d 5d 6d 7d]
        log.info(key);
        rocketMQTemplate.syncSend(topicName, MessageBuilder.withPayload(key).build(), 3000, level + 1);
    }

    @Override
    public Map<Integer, Long> getTimeMap() {
        List<String> timeLevel = listTimeLevel();
        HashMap<Integer, Long> timeMap = new HashMap<>();
        for (int i = 0; i < timeLevel.size(); i++) {
            String temp = timeLevel.get(i);
            if (temp.contains("秒")) {
                timeMap.put(i, Long.parseLong(temp.substring(0, temp.length() - 1)));
            } else if (temp.contains("分")) {
                timeMap.put(i, Long.parseLong(temp.substring(0, temp.length() - 1)) * 60);
            } else if (temp.contains("时")) {
                timeMap.put(i, Long.parseLong(temp.substring(0, temp.length() - 1)) * 60 * 60);
            } else {
                timeMap.put(i, Long.parseLong(temp.substring(0, temp.length() - 1)) * 60 * 60 * 24);
            }
        }
        return timeMap;
    }

    @Override
    public List<String> listTimeLevel() {
        String[] s = times.split(" ");
        List<String> timeLevels = new ArrayList<>();
        Arrays.stream(s).forEach(time ->{
            String temp = time.replace("s", "秒");
            temp = temp.replace("m", "分");
            temp = temp.replace("h", "时");
            temp = temp.replace("d", "天");
            timeLevels.add(temp);
        });
        return timeLevels;
    }
}
