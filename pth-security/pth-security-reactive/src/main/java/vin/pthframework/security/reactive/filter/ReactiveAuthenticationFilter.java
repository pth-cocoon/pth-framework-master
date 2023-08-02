package vin.pthframework.security.reactive.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import vin.pthframework.security.core.config.SecurityCoreProperties;
import vin.pthframework.security.core.consts.FilterOrderConst;
import vin.pthframework.security.core.exception.BaseSecurityException;
import vin.pthframework.security.core.util.RbacChecker;
import vin.pthframework.security.reactive.handler.AuthorizationFailureHandler;
import vin.pthframework.security.reactive.util.ReactiveSecurityContextHolder;

/**
 * @author Cocoon
 */
@Slf4j
@Order(FilterOrderConst.AUTHORIZATION)
@Component
@ConditionalOnProperty(prefix = "pth.security.core", value = "enable-authorization")
@RequiredArgsConstructor
public class ReactiveAuthenticationFilter implements WebFilter {

  private final AuthorizationFailureHandler authorizationFailureHandler;
  private final SecurityCoreProperties securityCoreProperties;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    var uri = request.getURI().getPath();
    var method = request.getMethod().name();
    ServerHttpResponse response = exchange.getResponse();
    Assert.notNull(request.getMethod(), "Method is null");
    if (RbacChecker.checkWhiteList(securityCoreProperties.getWhiteList(),
        request.getURI().getPath())) {
      log.info("{}路径属于白名单，跳过校验", request.getURI().getPath());
      return chain.filter(exchange);
    }

    return ReactiveSecurityContextHolder.getContext().flatMap(se -> {
      RbacChecker.check(se, method, uri);
      log.info("认证通过，进行后续流程");
      return chain.filter(exchange);
    }).onErrorResume(BaseSecurityException.class,
        e -> authorizationFailureHandler.failureHandler(response, e));
  }

}
