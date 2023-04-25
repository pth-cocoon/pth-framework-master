package vin.pth.security.servlet.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import vin.pth.security.core.config.SecurityCoreProperties;

/**
 * @author Cocoon
 */
@Slf4j
@Configuration
public class SessionConfig {

  @Bean
  public HttpSessionIdResolver httpSessionIdResolver(SecurityCoreProperties securityCoreProperties) {
    switch (securityCoreProperties.getSessionPosition()) {
      case COOKIE -> {
        return new CookieHttpSessionIdResolver();
      }
      case HEADER -> {
        return new HeaderHttpSessionIdResolver(securityCoreProperties.getSessionKey());
      }
      default -> {
        log.warn("session默认存储在cookie");
        return new CookieHttpSessionIdResolver();
      }
    }
  }


}
