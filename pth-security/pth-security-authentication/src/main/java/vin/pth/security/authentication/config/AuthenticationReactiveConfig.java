package vin.pth.security.authentication.config;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;
import vin.pth.security.authentication.filter.react.LoginReactiveFilter;
import vin.pth.security.authentication.handler.reactive.AuthenticationFailureHandler;
import vin.pth.security.authentication.handler.reactive.AuthenticationSuccessHandler;
import vin.pth.security.authentication.provider.ProviderManager;

/**
 * @author Cocoon
 * @date 2022/11/3
 */
@Slf4j
@Configuration
@ComponentScan("vin.pth.security.authentication")
@Import({ProviderManager.class, LoginReactiveFilter.class})
@EnableConfigurationProperties(AuthenticationProperties.class)
@ConditionalOnWebApplication(type = Type.REACTIVE)
public class AuthenticationReactiveConfig {

  @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
  @ConditionalOnWebApplication(type = Type.REACTIVE)
  @Bean
  public AuthenticationSuccessHandler successHandler() {
    log.info("AuthenticationReactiveConfig");
    return (exchange, userAuthInfo) -> {
      ServerHttpResponse response = exchange.getResponse();
      response.setStatusCode(HttpStatus.OK);
      DataBuffer dataBuffer = response.bufferFactory()
          .allocateBuffer().write(userAuthInfo.getToken().getBytes(StandardCharsets.UTF_8));
      return response.writeAndFlushWith(Mono.just(ByteBufMono.just(dataBuffer)));
    };
  }


  @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
  @ConditionalOnWebApplication(type = Type.REACTIVE)
  @Bean
  public AuthenticationFailureHandler failureHandler() {
    return (exchange, e) -> {
      ServerHttpResponse response = exchange.getResponse();
      response.setStatusCode(HttpStatus.UNAUTHORIZED);
      DataBuffer buff = response.bufferFactory()
          .allocateBuffer().write(e.getMessage().getBytes(StandardCharsets.UTF_8));
      return response.writeAndFlushWith(Mono.just(ByteBufMono.just(buff)));
    };
  }


}
