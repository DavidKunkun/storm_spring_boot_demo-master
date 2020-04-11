package com.maxplus1.demo.storm.bolt.builder;

import com.maxplus1.demo.storm.bolt.WordCountToRedisBolt;
import com.maxplus1.demo.utils.Serializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * Created by xiaolong.qiu on 2017/4/6.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "storm.spout.wordCountToRedisBolt")
@DependsOn("redisTemplate")
public class WordCountToRedisBoltBuilder extends BoltBuilder {

    @Autowired
    private RedisProperties redisProperties;

    @Bean("wordCountToRedisBolt")
    public WordCountToRedisBolt buildBolt() {
        super.setId("wordCountToRedisBolt");
        WordCountToRedisBolt wordCountToRedisBolt = new WordCountToRedisBolt();
        wordCountToRedisBolt.setRedisProperties(Serializer.INSTANCE.serialize(redisProperties));
        return wordCountToRedisBolt;
    }


}
