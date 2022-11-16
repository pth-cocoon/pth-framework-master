package vin.pth.security.authentication.filter.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import vin.pth.security.authentication.handler.sevlet.AuthenticationFailureHandler;
import vin.pth.security.authentication.handler.sevlet.AuthenticationSuccessHandler;
import vin.pth.security.authentication.provider.ProviderManager;
import vin.pth.security.core.constant.SecurityCoreConstant;
import vin.pth.security.core.exception.AuthenticationException;
import vin.pth.security.core.model.UserAuthInfo;

/**
 * @author Cocoon
 * @date 2022/11/3
 */
@Slf4j
@ConditionalOnWebApplication(type = Type.SERVLET)
@Component
@RequiredArgsConstructor
public class LoginFilter implements Filter {

  private final AuthenticationSuccessHandler successHandler;
  private final AuthenticationFailureHandler failureHandler;
  private final ProviderManager providerManager;
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws ServletException, IOException {
    HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
    log.info(this.getClass().getName());
    if (!support(httpRequest)) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }
    try {
      ContentCachingRequestWrapper wrapper =
          (ContentCachingRequestWrapper) httpRequest.getAttribute(
              ContentCachingRequestWrapper.class.getName());
      HttpSession session = httpRequest.getSession();
      UserAuthInfo authenticate = providerManager.authenticate(getBody(wrapper));
      session.setAttribute(SecurityCoreConstant.USER_AUTH_INFO_KEY, authenticate);
      successHandler.successHandler((HttpServletRequest) servletRequest,
          (HttpServletResponse) servletResponse, authenticate);
    } catch (AuthenticationException e) {
      failureHandler.failureHandler((HttpServletRequest) servletRequest,
          (HttpServletResponse) servletResponse, e);
    }

  }

  private Map<String, Object> getBody(ContentCachingRequestWrapper wrapper) {
    byte[] contentAsByteArray = wrapper.getContentAsByteArray();
    try {
      return OBJECT_MAPPER.readValue(contentAsByteArray, new TypeReference<>() {
      });
    } catch (IOException e) {
      log.error("e:", e);
      return Collections.emptyMap();
    }
  }

  private boolean support(HttpServletRequest servletRequest) {
    var uri = servletRequest.getRequestURI();
    return "/login".equalsIgnoreCase(uri) && servletRequest.getMethod()
        .equalsIgnoreCase(HttpMethod.POST.name());
  }
}
