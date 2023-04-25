package vin.pth.security.reactive.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;
import vin.pth.security.core.config.SecurityCoreProperties;
import vin.pth.security.core.pojo.UserAuthInfo;
import vin.pth.security.core.util.RbacChecker;
import vin.pth.security.reactive.repository.ReactiveSecurityContextRepository;

import javax.annotation.Resource;

/**
 * @author Cocoon
 */
@Slf4j
@Import({SecurityBeanConfig.class, SessionConfig.class})
@Configuration
@EnableWebFluxSecurity
public class SecurityReactiveConfig {

    private final ReactiveAuthorizationManager<AuthorizationContext> manager = (authentication, context) -> {
        String method = context.getExchange().getRequest().getMethod().name();
        String uri = context.getExchange().getRequest().getURI().getPath();
        return authentication.flatMap(auth -> {
            UserAuthInfo principal = (UserAuthInfo) auth.getDetails();
            RbacChecker.check(principal, method, uri);
            log.debug("权限认证通过: username={},method={},uri={}", principal.getUsername(), method, uri);
            return Mono.just(new AuthorizationDecision(true));
        }).onErrorResume(throwable -> Mono.just(new AuthorizationDecision(false)));
    };
    @Resource
    private SecurityProperties securityProperties;
    @Resource
    private ServerAuthenticationEntryPoint serverAuthenticationEntryPoint;
    @Resource
    private ServerAccessDeniedHandler serverAccessDeniedHandler;


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
        SecurityCoreProperties properties) {
        if (!properties.isEnableAuthorization()) {
            return http.authorizeExchange(spec -> spec.anyExchange().permitAll()).build();
        }
        http.authorizeExchange(spec -> {
                for (String uri : properties.getWhiteList()) {
                    spec.pathMatchers(uri).permitAll();
                }
                spec.anyExchange().access(manager);
            }).securityContextRepository(new ReactiveSecurityContextRepository())
            .exceptionHandling().accessDeniedHandler(serverAccessDeniedHandler)
            .authenticationEntryPoint(serverAuthenticationEntryPoint)
            .and().httpBasic().disable().formLogin().disable().csrf().disable();
        return http.build();
    }

}
