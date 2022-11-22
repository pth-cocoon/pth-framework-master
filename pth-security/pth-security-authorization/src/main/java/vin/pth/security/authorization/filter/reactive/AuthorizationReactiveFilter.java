package vin.pth.security.authorization.filter.reactive;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import vin.pth.security.authorization.component.RbacChecker;
import vin.pth.security.authorization.config.AuthorizationProperties;
import vin.pth.security.authorization.handler.reactive.AuthorizationFailureHandler;
import vin.pth.security.authorization.util.AuthCheckUtil;
import vin.pth.security.core.context.UserAuthReactiveHolder;
import vin.pth.security.core.exception.AuthorizationException;

/**
 * @author Cocoon
 * @date 2022/11/5
 */
@Slf4j
@ConditionalOnWebApplication(type = Type.REACTIVE)
@RequiredArgsConstructor
@Component
public class AuthorizationReactiveFilter implements WebFilter {

  private final RbacChecker rbacChecker;
  private final AuthorizationFailureHandler authorizationFailureHandler;
  private final AuthorizationProperties authorizationProperties;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    ServerHttpResponse response = exchange.getResponse();
    Assert.notNull(request.getMethod(), "Method is null");
    if (AuthCheckUtil.checkList(request.getMethod().name(), request.getURI().getPath(),
        authorizationProperties.getWhiteList())) {
      log.info("{}路径属于白名单，跳过校验", request.getURI().getPath());
      return chain.filter(exchange);
    }

    return UserAuthReactiveHolder.getUserAuthInfo().flatMap(userAuthInfo -> {

      rbacChecker.check(request.getMethod().name(), request.getURI().getPath(), userAuthInfo);
      return chain.filter(exchange);
    }).onErrorResume(AuthorizationException.class,
        e -> authorizationFailureHandler.failureHandler(response, e));
  }


}
