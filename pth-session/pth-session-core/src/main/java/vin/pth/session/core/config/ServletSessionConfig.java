package vin.pth.session.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vin.pth.session.core.filter.ServletSessionFilter;
import vin.pth.session.core.repository.SessionRepository;

/**
 * @author Cocoon
 * @date 2022/11/13
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@EnableConfigurationProperties(PthSessionProperties.class)
public class ServletSessionConfig {


  @Bean
  public ServletSessionFilter sessionFilter(SessionRepository sessionRepository,
      PthSessionProperties sessionProperties) {
    return new ServletSessionFilter(sessionRepository, sessionProperties);
  }

}
