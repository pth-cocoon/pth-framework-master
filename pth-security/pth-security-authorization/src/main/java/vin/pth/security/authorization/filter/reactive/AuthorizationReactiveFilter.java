package vin.pth.security.authorization.filter.reactive;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import vin.pth.security.authorization.component.RbacChecker;
import vin.pth.security.authorization.handler.reactive.AuthorizationFailureHandler;
import vin.pth.security.core.exception.AuthorizationException;
import vin.pth.security.core.model.UserAuthInfo;

/**
 * @author Cocoon
 * @date 2022/11/5
 */
@Slf4j
@ConditionalOnWebApplication(type = Type.REACTIVE)
@RequiredArgsConstructor
@Component
public class AuthorizationReactiveFilter implements WebFilter {

  private final static String DEFAULT_LOGIN_URI = "/login";
  private final RbacChecker rbacChecker;
  private final AuthorizationFailureHandler authorizationFailureHandler;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    ServerHttpResponse response = exchange.getResponse();
    if (inWhiteList(request.getURI().getPath())) {
      log.info("{}路径属于白名单，跳过校验", request.getURI().getPath());
      return chain.filter(exchange);
    }

    return exchange.getSession().flatMap(webSession -> {
      UserAuthInfo userAuthInfo = (UserAuthInfo) webSession.getAttributes().get("USER_INFO");
      log.info("UserInfo:{}", userAuthInfo);
      rbacChecker.check(request.getMethod().name(), request.getURI().getPath(), userAuthInfo);
      return chain.filter(exchange)
          .contextWrite(context -> context.put(UserAuthInfo.class, userAuthInfo));
    }).onErrorResume(AuthorizationException.class,
        e -> authorizationFailureHandler.failureHandler(response, e));
  }

  private boolean inWhiteList(String uri) {
    if (DEFAULT_LOGIN_URI.equals(uri)) {
      return true;
    }
    return false;
  }
}
