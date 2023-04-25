package vin.pth.security.reactive.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;
import vin.pth.security.core.enums.SecurityErrorCode;
import vin.pth.security.core.util.ResultUtil;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cocoon
 */
@Slf4j
@Configuration
public class SecurityBeanConfig {

  @ConditionalOnMissingBean(ServerAccessDeniedHandler.class)
  @Bean
  public ServerAccessDeniedHandler serverAccessDeniedHandler() {
    log.warn("默认的鉴权失败处理器，根据业务需要覆盖Bean......");
    return (exchange, denied) -> Mono
        .defer(() -> Mono.just(exchange.getResponse()))
        .flatMap(response -> {
          log.error("e", denied);
          response.setStatusCode(HttpStatus.FORBIDDEN);
          response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
          Map<String, Object> resultMap = ResultUtil.error(SecurityErrorCode.NOT_LOGIN);
          DataBuffer buffer = response.bufferFactory()
              .wrap(ResultUtil.toResultStr(resultMap).getBytes(Charset.defaultCharset()));
          return response.writeWith(Mono.just(buffer));
        });
  }

  @ConditionalOnMissingBean(ServerAuthenticationEntryPoint.class)
  @Bean
  public ServerAuthenticationEntryPoint serverAuthenticationEntryPoint() {
    log.warn("默认的未登录拦截，根据业务需要覆盖Bean......");
    return (exchange, ex) -> Mono.defer(() -> Mono.just(exchange.getResponse()))
        .flatMap(response -> {
          log.error("e", ex);
          response.setStatusCode(HttpStatus.UNAUTHORIZED);
          response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
          Map<String, Object> resultMap = ResultUtil.error(SecurityErrorCode.NOT_LOGIN);
          DataBuffer buffer = response.bufferFactory().wrap(ResultUtil.toResultStr(resultMap).getBytes(
              Charset.defaultCharset()));
          return response.writeWith(Mono.just(buffer));
        });
  }


  @Primary
  @Bean
  public MapReactiveUserDetailsService myReactiveUserDetailsService() {
    return new MapReactiveUserDetailsService(new HashMap<>());

  }


}
