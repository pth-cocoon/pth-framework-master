package vin.pthframework.security.reactive.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;
import vin.pthframework.security.reactive.util.LoginUtil;
import vin.pthframework.session.consts.SecurityConst;
import vin.pthframework.session.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
@Slf4j
public class ServerSecurityContextRepository {


  public Mono<Void> save(ServerWebExchange exchange, UserAuthInfo authInfo) {
    return exchange.getSession().doOnNext(session -> {
      if (authInfo != null) {
        UserAuthInfo userAuthInfo =
            (UserAuthInfo) session.getAttributes().get(SecurityConst.USER_INFO_KEY);
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

  public Mono<UserAuthInfo> load(ServerWebExchange exchange) {
    return exchange.getSession().flatMap(session -> {
      var userAuthInfo = LoginUtil.getAuthInfo(session);
      return Mono.justOrEmpty(userAuthInfo);
    });
  }
}
