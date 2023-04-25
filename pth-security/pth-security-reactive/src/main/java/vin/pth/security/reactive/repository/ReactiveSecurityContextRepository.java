package vin.pth.security.reactive.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;
import vin.pth.security.core.consts.SecurityConst;
import vin.pth.security.core.pojo.UserAuthInfo;
import vin.pth.security.core.util.UserAuthUtil;

/**
 * @author Cocoon
 */
@Slf4j
public class ReactiveSecurityContextRepository implements ServerSecurityContextRepository {


    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return exchange.getSession().doOnNext(session -> {
            if (context != null) {
                UserAuthInfo userAuthInfo = (UserAuthInfo) session.getAttributes().get(SecurityConst.USER_INFO_KEY);
                Authentication authentication = UserAuthUtil.toAuth(userAuthInfo);
                context.setAuthentication(authentication);
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

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return exchange.getSession().flatMap(session -> {
            UserAuthInfo userAuthInfo = (UserAuthInfo) session.getAttributes().get(SecurityConst.USER_INFO_KEY);
            Authentication authentication = UserAuthUtil.toAuth(userAuthInfo);
            var context = new SecurityContextImpl(authentication);
            return Mono.justOrEmpty(context);
        });
    }
}
