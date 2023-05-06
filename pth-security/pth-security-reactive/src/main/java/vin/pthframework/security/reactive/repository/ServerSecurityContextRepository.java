package vin.pthframework.security.reactive.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;
import vin.pthframework.security.core.consts.SecurityConst;
import vin.pthframework.security.core.context.SecurityContext;
import vin.pthframework.session.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
@Slf4j
public class ServerSecurityContextRepository {


  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    return exchange.getSession().doOnNext(session -> {
      if (context != null) {
        UserAuthInfo userAuthInfo =
            (UserAuthInfo) session.getAttributes().get(SecurityConst.USER_INFO_KEY);
        context.setAuthInfo(userAuthInfo);
        if (userAuthInfo == null) {
          session.getAttributes().remove(SecurityConst.USER_INFO_KEY);
        } else {
          session.getAttributes().put(SecurityConst.USER_INFO_KEY, userAuthInfo);
        }
      } else {
        session.getAttributes().remove(SecurityConst.USER_INFO_KEY);
      }
    }).flatMap(WebSession::changeSessionId);
  }

  public Mono<SecurityContext> load(ServerWebExchange exchange) {
    return exchange.getSession().flatMap(session -> {
      var userAuthInfo = (UserAuthInfo) session.getAttributes().get(SecurityConst.USER_INFO_KEY);
      var context = new SecurityContext();
      if (userAuthInfo != null) {
        context.setAuthInfo(userAuthInfo);
      }
      return Mono.just(context);
    });
  }
}
