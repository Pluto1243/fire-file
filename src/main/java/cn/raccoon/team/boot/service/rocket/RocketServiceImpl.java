package cn.raccoon.team.boot.service.rocket;

import cn.raccoon.team.boot.entity.User;
import cn.raccoon.team.boot.service.IRocketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class RocketServiceImpl implements IRocketService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.time-level}")
    private String times;

    @Override
    public void sendInfo(String topicName, String info, Integer level) {
        User user = new User();
        user.setToken(info);
        // 发送同步消息
        // 时间精度 [1m 5m 10m 20m 30m 45m 1h 2h 3h 6h 12h 1d 2d 3d 4d 5d 6d 7d]
        log.info(user.toString());
        rocketMQTemplate.syncSend(topicName, MessageBuilder.withPayload(user).build(), 3000, level);
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
