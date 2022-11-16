package vin.pth.security.authorization.filter.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.stereotype.Component;
import vin.pth.security.authorization.component.RbacChecker;
import vin.pth.security.authorization.handler.servlet.AuthorizationFailureHandler;
import vin.pth.security.core.context.UserAuthServletHolder;
import vin.pth.security.core.exception.AuthorizationException;

/**
 * @author Cocoon
 * @date 2022/11/5
 */
@Slf4j
@ConditionalOnWebApplication(type = Type.SERVLET)
@RequiredArgsConstructor
@Component
public class AuthorizationServletFilter implements Filter {

  private final RbacChecker rbacChecker;
  private final AuthorizationFailureHandler authorizationFailureHandler;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    try {
      if (!inWhiteList(request.getRequestURI())) {
        rbacChecker.check(request.getMethod(), request.getRequestURI(),
            UserAuthServletHolder.getUserAuthInfo());
      }
      filterChain.doFilter(servletRequest, servletResponse);
    } catch (AuthorizationException e) {
      authorizationFailureHandler.failureHandler(response, e);
    } finally {
      UserAuthServletHolder.clear();
    }
  }

  private boolean inWhiteList(String uri) {
    return "/login".equals(uri);
  }
}
