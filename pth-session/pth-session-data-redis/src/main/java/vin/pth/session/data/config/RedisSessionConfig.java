package vin.pth.session.data.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import vin.pth.session.data.repository.RedisSessionRepository;

/**
 * @author Cocoon
 * @date 2022/11/14
 */
@ConditionalOnProperty(value = "pth.session.store-type", havingValue = "REDIS")
@Configuration
@Import(RedisSessionRepository.class)
@EnableConfigurationProperties({PthRedisSessionProperties.class})
public class RedisSessionConfig {


}
