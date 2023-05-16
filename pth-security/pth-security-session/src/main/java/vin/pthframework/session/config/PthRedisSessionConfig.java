package vin.pthframework.session.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import vin.pthframework.session.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
@ConditionalOnClass({RedisTemplate.class, RedisIndexedSessionRepository.class})
@Configuration
public class PthRedisSessionConfig {


  @Bean
  public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.addMixIn(UserAuthInfo.class, UserAuthInfo.class);
    return new GenericJackson2JsonRedisSerializer(mapper);
  }


}