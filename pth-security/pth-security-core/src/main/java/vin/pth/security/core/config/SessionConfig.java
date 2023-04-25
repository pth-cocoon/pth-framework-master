package vin.pth.security.core.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import vin.pth.security.core.pojo.UserAuthInfo;

import java.util.List;

/**
 * @author Cocoon
 */
@Configuration
public class SessionConfig implements BeanClassLoaderAware {

  private ClassLoader loader;

  @Bean
  public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
    ObjectMapper mapper = new ObjectMapper();
    List<Module> modules = SecurityJackson2Modules.getModules(this.loader);
    mapper.registerModules(modules);
    mapper.addMixIn(UserAuthInfo.class, UserAuthInfo.class);
    return new GenericJackson2JsonRedisSerializer(mapper);
  }

  @Override
  public void setBeanClassLoader(ClassLoader classLoader) {
    this.loader = classLoader;
  }

}