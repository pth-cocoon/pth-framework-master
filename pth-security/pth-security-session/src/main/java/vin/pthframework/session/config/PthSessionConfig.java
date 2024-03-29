package vin.pthframework.session.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

/**
 * @author Cocoon
 */
@EnableConfigurationProperties(PthSessionProperties.class)
@Import(PthRedisSessionConfig.class)
@Configuration
@Slf4j
public class PthSessionConfig {

  @EventListener
  public void onSessionCreated(SessionCreatedEvent event) {
    log.info("session生成成功，id:{},session过期时间:{}", event.getSessionId(),
        event.getSession().getCreationTime().getNano());
  }


  @ConditionalOnWebApplication(type = Type.SERVLET)
  @Bean
  public HttpSessionIdResolver httpSessionIdResolver(PthSessionProperties securityCoreProperties) {
    switch (securityCoreProperties.getSessionPosition()) {
      case COOKIE -> {
        log.info("session.mode=cookie");
        return new CookieHttpSessionIdResolver();
      }
      case HEADER -> {
        log.info("session.mode=header,sessionKey:{}",securityCoreProperties.getSessionKey());
        return new HeaderHttpSessionIdResolver(securityCoreProperties.getSessionKey());
      }
      default -> {
        log.warn("session默认存储在cookie");
        return new CookieHttpSessionIdResolver();
      }
    }
  }

  @ConditionalOnWebApplication(type = Type.REACTIVE)
  @Primary
  @Bean
  public WebSessionIdResolver myWebSessionIdResolver(PthSessionProperties securityCoreProperties) {
    switch (securityCoreProperties.getSessionPosition()) {
      case COOKIE -> {
        CookieWebSessionIdResolver cookieWebSessionIdResolver = new CookieWebSessionIdResolver();
        cookieWebSessionIdResolver.setCookieName(securityCoreProperties.getSessionKey());
        return cookieWebSessionIdResolver;
      }
      case HEADER -> {
        HeaderWebSessionIdResolver resolver = new HeaderWebSessionIdResolver();
        resolver.setHeaderName(securityCoreProperties.getSessionKey());
        return resolver;
      }
      default -> {
        log.warn("session默认存储在cookie");
        return new CookieWebSessionIdResolver();
      }
    }
  }


}
