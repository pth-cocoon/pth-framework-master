package vin.pth.security.authorization.config;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import vin.pth.security.authorization.handler.reactive.AuthorizationFailureHandler;

/**
 * @author Cocoon
 * @date 2022/11/16
 */
@Slf4j
@ConditionalOnWebApplication(type = Type.REACTIVE)
@Configuration
public class AuthorizationReactiveConfig {

  @Bean
  public AuthorizationFailureHandler authorizationFailureHandler() {
    return (response, e) -> {
      log.error("鉴权失败");
      String msg = e.getMessage();
      response.setStatusCode(HttpStatus.FORBIDDEN);
      DataBuffer buffer = response.bufferFactory().wrap(msg.getBytes(StandardCharsets.UTF_8));
      return response.writeWith(Flux.just(buffer));
    };

  }

}
