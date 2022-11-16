package vin.pth.security.authorization.handler.reactive;

import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import vin.pth.security.core.exception.AuthorizationException;

/**
 * @author Cocoon
 * @date 2022/11/15
 */
public interface AuthorizationFailureHandler {

  /**
   * @param response ServerHttpResponse
   * @param e        AuthorizationException
   * @return Mono
   */
  Mono<Void> failureHandler(ServerHttpResponse response, AuthorizationException e);

}
