package vin.pthframework.security.servlet.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import vin.pthframework.security.core.config.SecurityCoreProperties;
import vin.pthframework.security.core.consts.FilterOrderConst;
import vin.pthframework.security.core.exception.BaseSecurityException;
import vin.pthframework.security.core.util.RbacChecker;
import vin.pthframework.security.servlet.handler.AuthorizationFailureHandler;
import vin.pthframework.security.servlet.util.UserAuthInfoHolder;

/**
 * @author Cocoon
 */
@Slf4j
@RequiredArgsConstructor
@Component
@ConditionalOnProperty(prefix = "pth.security.core", value = "enable-authorization")
@Order(FilterOrderConst.AUTHORIZATION)
public class ServletAuthenticationFilter implements Filter {

  private final AuthorizationFailureHandler authorizationFailureHandler;
  private final SecurityCoreProperties securityCoreProperties;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    var request = (HttpServletRequest) servletRequest;
    String method = request.getMethod();
    String uri = request.getRequestURI();
    if (RbacChecker.checkWhiteList(securityCoreProperties.getWhiteList(), uri)) {
      log.info("[{}]处于白名单，放行", uri);
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }
    try {
      RbacChecker.check(UserAuthInfoHolder.getUserAuthInfo(), method, uri);
      log.info("认证通过，放行：userid:{},uri:{},method:{}",
          UserAuthInfoHolder.getUserAuthInfo().getId(), uri, method);
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (BaseSecurityException e) {
      log.error("e", e);
      authorizationFailureHandler.failureHandler((HttpServletResponse) servletResponse, e);
    }
  }
}
