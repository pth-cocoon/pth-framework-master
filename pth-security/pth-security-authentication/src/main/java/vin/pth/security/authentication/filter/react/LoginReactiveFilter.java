package vin.pth.security.authentication.filter.react;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import vin.pth.security.authentication.handler.reactive.AuthenticationFailureHandler;
import vin.pth.security.authentication.handler.reactive.AuthenticationSuccessHandler;
import vin.pth.security.authentication.provider.ProviderManager;
import vin.pth.security.core.exception.AuthenticationException;
import vin.pth.security.core.model.UserAuthInfo;

/**
 * @author Cocoon
 * @date 2022/11/3
 */
@Slf4j
@ConditionalOnWebApplication(type = Type.REACTIVE)
@Component
@RequiredArgsConstructor
public class LoginReactiveFilter implements WebFilter {

  private final AuthenticationSuccessHandler authenticationSuccessHandler;
  private final AuthenticationFailureHandler authenticationFailureHandler;
  private final ProviderManager providerManager;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    if (!support(exchange.getRequest())) {
      return chain.filter(exchange);
    }
    try {
      UserAuthInfo authenticate = providerManager.authenticate(Collections.emptyMap());
      return authenticationSuccessHandler.successHandler(exchange, authenticate);
    } catch (AuthenticationException e) {
      return authenticationFailureHandler.failureHandler(exchange, e);
    }
  }

  private boolean support(ServerHttpRequest servletRequest) {
    log.error("{},暂不支持Webflux授权，请通过Controller自行实现", servletRequest.getURI().getPath());
    return false;
  }


}
