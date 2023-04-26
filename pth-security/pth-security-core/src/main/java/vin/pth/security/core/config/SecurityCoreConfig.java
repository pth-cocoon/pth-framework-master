package vin.pth.security.core.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import vin.pth.security.core.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
@ImportAutoConfiguration(SecurityCoreProperties.class)
@Configuration
public class SecurityCoreConfig implements BeanClassLoaderAware {

  private ClassLoader loader;

  @ConditionalOnMissingBean(PasswordEncoder.class)
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModules(SecurityJackson2Modules.getModules(this.loader));
    mapper.addMixIn(UserAuthInfo.class, UserAuthInfo.class);
    return new GenericJackson2JsonRedisSerializer(mapper);
  }

  @Override
  public void setBeanClassLoader(ClassLoader classLoader) {
    this.loader = classLoader;
  }


}
