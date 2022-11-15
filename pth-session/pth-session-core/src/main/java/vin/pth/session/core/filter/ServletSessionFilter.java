package vin.pth.session.core.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.util.CollectionUtils;
import vin.pth.session.core.config.PthSessionProperties;
import vin.pth.session.core.context.ServletSessionHolder;
import vin.pth.session.core.repository.SessionRepository;

/**
 * @author Cocoon
 * @date 2022/11/14
 */
@RequiredArgsConstructor
public final class ServletSessionFilter implements OrderedFilter {

  private final SessionRepository sessionRepository;
  private final PthSessionProperties properties;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    String sessionId = switch (properties.getRequestPosition()) {
      case COOKIE -> getSessionInCookie(httpServletRequest);
      case HEADER -> getSessionIdInHeader(httpServletRequest);
    };
    var session = sessionRepository.getOrCreateSession(sessionId);
    ServletSessionHolder.setSession(session);
    try {
      filterChain.doFilter(servletRequest, servletResponse);
      session = ServletSessionHolder.getSession();
      Cookie sessionCookie = new Cookie(properties.getSessionIdKey(), session.getSessionId());
      httpServletResponse.addCookie(sessionCookie);
    } finally {
      ServletSessionHolder.clearSession();
      sessionRepository.commitSession(session);
    }
  }

  private String getSessionInCookie(HttpServletRequest request) {
    List<Cookie> cookies = Arrays.stream(request.getCookies())
        .filter(a -> a.getName().equals(properties.getSessionIdKey())).toList();
    if (CollectionUtils.isEmpty(cookies)) {
      return "";
    }
    return cookies.get(0).getValue();
  }

  private String getSessionIdInHeader(HttpServletRequest request) {
    return request.getHeader(properties.getSessionIdKey());
  }

  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE;
  }
}
