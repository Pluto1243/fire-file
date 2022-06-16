package cn.raccoon.team.boot.service.impl;

import cn.raccoon.team.boot.entity.FireFile;
import cn.raccoon.team.boot.entity.KeyCode;
import cn.raccoon.team.boot.exception.CommonException;
import cn.raccoon.team.boot.exception.EmError;
import cn.raccoon.team.boot.service.IFireFileService;
import cn.raccoon.team.boot.service.IRocketService;
import cn.raccoon.team.boot.utils.RandomUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
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
    public KeyCode sendFireFile(FireFile fireFile) {
        // 生成随机链接
        String key = RandomUtils.generateByRandom(8);
        fireFile.setKey(key);
        // 生成随机提取码 TODO 需要优化 不能用全数字 不然可以穷举出验证码
        String code = String.valueOf((int)(Math.floor((Math.random() * 9 + 1) * 1000)));
        fireFile.setCode(code);
        // 发送延时消息，通知删除
        if (fireFile.getExpire() && fireFile.getExpireLevel() != null) {
            rocketService.sendFire(TOPIC_NAME, fireFile.getKey(), fireFile.getExpireLevel());
            // 获取时间
            Map<Integer, Long> timeMap = rocketService.getTimeMap();

            // 过期时间
            Long expireTime = timeMap.get(fireFile.getExpireLevel()) * 1000;

            // 设置过期时间
            fireFile.setExpireDate(new Date(System.currentTimeMillis() + expireTime));
            // 将数据缓存到redis
            // TODO 后续需要做持久化
            redisTemplate.opsForValue().set(key, JSONObject.toJSONString(fireFile), timeMap.get(fireFile.getExpireLevel()) + 5, TimeUnit.SECONDS);
        } else {
            // 30天后过期
            redisTemplate.opsForValue().set(key, JSONObject.toJSONString(fireFile), 60 * 60 * 24 * 30, TimeUnit.SECONDS);
        }
        // 返回链接和提取码
        KeyCode keyCode = new KeyCode();
        keyCode.setKey(prefix + key);
        keyCode.setCode(code);
        return keyCode;
    }

    @Override
    public FireFile getFileInfoByKey(String key) {
        // 根据key获取到文件信息
        Object firefile = redisTemplate.opsForValue().get(key);
        if (firefile != null) {
            FireFile fireFile = JSONObject.parseObject(firefile.toString(), FireFile.class);
            // 移除不能展示给用户的数据
            fireFile.setPath(null);
            fireFile.setCode(null);
            fireFile.setExpireLevel(null);
            return fireFile;
        }
        // key不存在  抛出错误
        throw new CommonException(EmError.URL_NOT_FOUNT);
    }

    @Override
    public void extractFile(String key, String code, HttpServletResponse response) {
        // 根据key获取到文件信息
        Object fireFile = redisTemplate.opsForValue().get(key);
        if (fireFile != null) {
            FireFile file = JSONObject.parseObject(fireFile.toString(), FireFile.class);

            // 有验证码且验证码错误
            if (file.getCode() != null && !file.getCode().equals(code)) {
                throw new CommonException(EmError.CODE_FAILD);
            }

            // 设置返回信息数据
            response.setContentType("text/plain;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("content-disposition", "attachment;filename=" + file.getShowName());

            // 创建输出流
            BufferedOutputStream outSos = null;
            File realFile = new File(file.getPath());
            BufferedInputStream bis = null;

            try {
                outSos = new BufferedOutputStream(response.getOutputStream());
                bis = new BufferedInputStream(new FileInputStream(file.getPath()));
                // 文件不存在
                if (!realFile.exists() || bis == null) {
                    throw new CommonException(EmError.URL_NOT_FOUNT);
                }
                // 写入输出流
                int len;
                byte[] bytes = new byte[1024];
                while ((len = bis.read(bytes, 0, bytes.length)) != -1) {
                    outSos.write(bytes, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // 下载成功 TODO 剩余下载数-1
                    // TODO 删除key和文件 持久化到数据库
                    rocketService.sendFire(TOPIC_NAME, key, -1);
                    bis.close();;
                    outSos.flush();
                    outSos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            // key不存在  抛出错误
            throw new CommonException(EmError.URL_NOT_FOUNT);
        }
    }
}
