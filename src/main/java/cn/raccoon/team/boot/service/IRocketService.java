package cn.raccoon.team.boot.service;

import java.util.List;
import java.util.Map;

/**
 * 消息队列服务
 *
 * @author wangjie
 * @date 17:49 2022年06月01日
 **/
public interface IRocketService {

    /**
     * @param topicName topicName
     * @param key   key
     * @param level 时间精度
     * @return void
     * @description 延时删除
     * @author wangjie
     * @date 17:50 2022年06月13日
     */
    void sendFire(String topicName, String key, Integer level);

    /**
     * @description 获得时间精度
     *
     * @author wangjie
     * @date 18:03 2022年06月13日
     * @param
     * @return java.util.Map<java.lang.Integer,java.lang.Long>
     */
    Map<Integer, Long> getTimeMap();

    /**
     * @description 获得时间精度集合
     *
     * @author wangjie
     * @date 18:04 2022年06月13日
     * @param
     * @return java.util.List<java.lang.String>
     */
    List<String> listTimeLevel();
}
