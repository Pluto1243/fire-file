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

import javax.servlet.ServletOutputStream;
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
        // 构建fireFile
        build(fireFile);

        // 获取过期时间map
        Map<Integer, Long> timeMap = rocketService.getTimeMap();

        // 发送延时消息，通知删除
        if (fireFile.getExpire() && fireFile.getExpireLevel() != null) {
            rocketService.sendFire(TOPIC_NAME, fireFile.getKey(), fireFile.getExpireLevel());
            // 将数据缓存到redis
            // TODO 后续需要做持久化
            redisTemplate.opsForValue().set(fireFile.getKey(), JSONObject.toJSONString(fireFile), timeMap.get(fireFile.getExpireLevel()) + 5, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(fireFile.getKey(), JSONObject.toJSONString(fireFile), 60 * 60 * 24 * 30, TimeUnit.SECONDS);
        }
        // 返回链接和提取码
        KeyCode keyCode = new KeyCode();
        keyCode.setKey(fireFile.getKey());
        keyCode.setCode(fireFile.getCode());
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
            response.setHeader("content-disposition", "attachment;filename=" + file.getShowName() + file.getFileName().substring(file.getFileName().lastIndexOf(".")));

            try {
                // 下载文件
                fileDownload(file.getPath(), response.getOutputStream());
                // 下载成功 TODO 剩余下载数-1
                // TODO 删除key和文件 持久化到数据库
                rocketService.sendFire(TOPIC_NAME, key, -1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // key不存在  抛出错误
            throw new CommonException(EmError.URL_NOT_FOUNT);
        }
    }

    /**
     * @description 构建阅后即焚信息
     *
     * @author wangjie
     * @date 16:32 2022年06月23日
     * @param fireFile 阅后即焚文件
     */
    private void build(FireFile fireFile) {
        // 获取文件后缀名
        String suffixName = fireFile.getFileName().substring(fireFile.getFileName().lastIndexOf("."));

        // 拼接到文件展示名上
        fireFile.setShowName(fireFile.getShowName() + suffixName);

        // 生成随机链接
        String key = RandomUtils.generateByRandom(8);
        fireFile.setKey(key);

        // 生成随机提取码
        String code = RandomUtils.generateByRandom(4);
        fireFile.setCode(code);

        // 判断过期情况
        if (fireFile.getExpire() && fireFile.getExpireLevel() != null) {
            // 获取时间
            Map<Integer, Long> timeMap = rocketService.getTimeMap();

            // 过期时间
            Long expireTime = timeMap.get(fireFile.getExpireLevel()) * 1000;

            // 设置过期时间
            fireFile.setExpireDate(new Date(System.currentTimeMillis() + expireTime));
        } else {
            // 30天后过期
            fireFile.setExpireDate(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 30 * 1000L));
        }
    }

    /**
     * @description 将路径下的文件写入到输出流里
     *
     * @author wangjie
     * @date 17:06 2022年06月23日
     * @param path 文件路径
     * @param servletOutputStream 输出流
     * @return void
     */
    private void fileDownload(String path, ServletOutputStream servletOutputStream) {
        // 创建输出流
        BufferedOutputStream outSos = null;
        File realFile = new File(path);
        BufferedInputStream bis = null;

        try {
            outSos = new BufferedOutputStream(servletOutputStream);
            bis = new BufferedInputStream(new FileInputStream(path));
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
                bis.close();;
                outSos.flush();
                outSos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
