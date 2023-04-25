package vin.pth.security.servlet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import vin.pth.security.core.config.SecurityCoreProperties;
import vin.pth.security.core.consts.SecurityConst;
import vin.pth.security.core.pojo.UserAuthInfo;
import vin.pth.security.core.util.RbacChecker;
import vin.pth.security.servlet.repository.ServletSecurityContextRepository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Cocoon
 */
@EnableWebSecurity
@Import({SecurityBeanConfig.class, SessionConfig.class})
@Configuration
public class SecurityServletConfig {

    private final AuthorizationManager<RequestAuthorizationContext> manager = (authentication, context) -> {
        HttpServletRequest request = context.getRequest();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        UserAuthInfo userAuthInfo = (UserAuthInfo) request.getSession().getAttribute(SecurityConst.USER_INFO_KEY);
        if (userAuthInfo == null) {
            return new AuthorizationDecision(false);
        }
        RbacChecker.check(userAuthInfo, method, uri);
        return new AuthorizationDecision(true);

    };
    @Resource
    private AccessDeniedHandler accessDeniedHandler;
    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Order(1)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, SecurityCoreProperties properties) throws Exception {
        if (!properties.isEnableAuthorization()) {
            return http.authorizeHttpRequests().anyRequest().permitAll().and().build();
        }
        http.authorizeHttpRequests()
            .antMatchers(properties.getWhiteList().toArray(new String[0])).permitAll()
            .anyRequest().access(manager).and()
            .securityContext().securityContextRepository(new ServletSecurityContextRepository())
            .and()
            .csrf().disable()
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
            .authenticationEntryPoint(authenticationEntryPoint);
        return http.build();
    }


}
