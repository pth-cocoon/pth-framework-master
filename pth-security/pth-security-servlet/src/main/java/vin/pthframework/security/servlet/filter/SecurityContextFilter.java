package vin.pthframework.security.servlet.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
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

  private ServletSecurityContextRepository servletSecurityContextRepository =
      new ServletSecurityContextRepository();

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    servletSecurityContextRepository.saveContext((HttpServletRequest) servletRequest);
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
