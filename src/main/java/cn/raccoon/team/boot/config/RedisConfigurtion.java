package cn.raccoon.team.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfigurtion {

  @Autowired
  private RedisTemplate redisTemplate;


  @Bean
  public RedisSerializer fastJson2JsonRedisSerializer() {
    return new FastJson2JsonRedisSerializer<Object>(Object.class);
  }

  /**
   * @description 解决字符串经过java序列化后有乱码问题
   *
   * @author wangjie
   * @date 15:09 2022年04月25日
   * @param
   * @return org.springframework.data.redis.core.RedisTemplate<java.lang.String, java.lang.Object>
   */
  @Bean
  public RedisTemplate<Object, Object> stringSerializerRedisTemplate() {
    // 设置key和value的序列化规则
    // 用fastjson替换jackjson
    redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.afterPropertiesSet();

    return redisTemplate;
  }
}
