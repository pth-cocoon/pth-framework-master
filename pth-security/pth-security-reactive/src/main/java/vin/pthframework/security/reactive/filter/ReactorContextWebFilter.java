package vin.pthframework.security.reactive.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import vin.pthframework.security.core.consts.FilterOrderConst;
import vin.pthframework.security.reactive.repository.ServerSecurityContextRepository;
import vin.pthframework.security.reactive.util.ReactiveSecurityContextHolder;
import vin.pthframework.session.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
@Slf4j
@Order(FilterOrderConst.CONTEXT)
@Component
@RequiredArgsConstructor
public class ReactorContextWebFilter implements WebFilter {

  private final ServerSecurityContextRepository repository = new ServerSecurityContextRepository();


  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

    return chain.filter(exchange).subscriberContext(
        context -> context.hasKey(UserAuthInfo.class) ? context
            : withSecurityContext(context, exchange));
  }

  private Context withSecurityContext(Context mainContext, ServerWebExchange exchange) {
    return mainContext
        .putAll(this.repository.load(exchange).as(ReactiveSecurityContextHolder::withUserAuthInfo));
  }

}

