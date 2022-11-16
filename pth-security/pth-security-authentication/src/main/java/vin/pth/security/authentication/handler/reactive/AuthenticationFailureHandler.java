package vin.pth.security.authentication.handler.reactive;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import vin.pth.security.core.exception.AuthenticationException;

/**
 * @author Cocoon
 * @date 2022/11/4
 */
public interface AuthenticationFailureHandler {

  /**
   * 登陆成功处理.
   *
   * @param response ServerWebExchange
   * @param e        权限认证异常.
   */
  Mono<Void> failureHandler(ServerWebExchange response, AuthenticationException e);


}
