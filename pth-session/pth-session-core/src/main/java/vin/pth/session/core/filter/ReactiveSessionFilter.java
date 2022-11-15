package vin.pth.session.core.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.filter.OrderedWebFilter;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import vin.pth.session.core.config.PthSessionProperties;
import vin.pth.session.core.context.ReactiveSessionHolder;
import vin.pth.session.core.repository.SessionRepository;

/**
 * @author Cocoon
 * @date 2022/11/13
 */
@RequiredArgsConstructor
public final class ReactiveSessionFilter implements OrderedWebFilter {

  private final SessionRepository sessionRepository;
  private final PthSessionProperties properties;

  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    var sessionId = switch (properties.getRequestPosition()) {
      case COOKIE -> getSessionInCookie(exchange.getRequest());
      case HEADER -> getSessionIdInHeader(exchange.getRequest());
    };
    var session = sessionRepository.getOrCreateSession(sessionId);
    var cookie = ResponseCookie.from(properties.getSessionIdKey(), session.getSessionId()).build();
    exchange.getResponse().addCookie(cookie);
    return chain.filter(exchange)
        .contextWrite(context -> ReactiveSessionHolder.withSession(session))
        .doFinally(signalType -> sessionRepository.commitSession(session));
  }

  private String getSessionInCookie(ServerHttpRequest request) {
    HttpCookie first = request.getCookies().getFirst(properties.getSessionIdKey());
    if (first != null) {
      return first.getValue();
    }
    return "";
  }

  private String getSessionIdInHeader(ServerHttpRequest request) {
    return request.getHeaders().getFirst(properties.getSessionIdKey());
  }

}
