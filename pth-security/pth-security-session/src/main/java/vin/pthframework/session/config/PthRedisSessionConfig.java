package vin.pthframework.session.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;

@ConditionalOnClass({RedisTemplate.class, RedisIndexedSessionRepository.class})
@Configuration
public class PthRedisSessionConfig {


}