package vin.pthframework.security.reactive.handler;

import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import vin.pthframework.security.core.exception.BaseSecurityException;

/**
 * @author Cocoon
 * @date 2022/11/15
 */
public interface AuthorizationFailureHandler {

  /**
   * 鉴权失败处理器.
   *
   * @param response ServerHttpResponse
   * @param e        AuthorizationException
   * @return Mono
   */
  Mono<Void> failureHandler(ServerHttpResponse response, BaseSecurityException e);

}
