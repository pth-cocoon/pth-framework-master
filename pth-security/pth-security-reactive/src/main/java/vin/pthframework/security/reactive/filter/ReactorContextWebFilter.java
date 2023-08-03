package vin.pthframework.security.reactive.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.session.WebSessionManager;
import reactor.core.publisher.Mono;
import vin.pthframework.security.core.consts.FilterOrderConst;
import vin.pthframework.security.reactive.repository.ServerSecurityContextRepository;
import vin.pthframework.security.reactive.util.LoginUtil;
import vin.pthframework.session.consts.SecurityConst;

/**
 * @author Cocoon
 */
@Slf4j
@Order(FilterOrderConst.CONTEXT)
@Component
@RequiredArgsConstructor
public class ReactorContextWebFilter implements WebFilter {

  private final WebSessionManager webSessionManager;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

    return webSessionManager.getSession(exchange)
        .flatMap(session -> {
          var userInfo = LoginUtil.getAuthInfo(session);
          log.info("处理用户信息,当前用户,{}", userInfo);
          if (userInfo != null) {
            exchange.getAttributes().put(SecurityConst.USER_INFO_KEY, userInfo);
          }
          return chain.filter(exchange);
        });
  }


}

