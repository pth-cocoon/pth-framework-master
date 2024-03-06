package vin.pthframework.security.servlet.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import vin.pthframework.security.core.consts.FilterOrderConst;
import vin.pthframework.security.servlet.repository.ServletSecurityContextRepository;

/**
 * @author Cocoon
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Order(FilterOrderConst.CONTEXT)
public class SecurityContextFilter implements Filter {

  private final ServletSecurityContextRepository servletSecurityContextRepository =
      new ServletSecurityContextRepository();

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    servletSecurityContextRepository.saveContext((HttpServletRequest) servletRequest);
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
