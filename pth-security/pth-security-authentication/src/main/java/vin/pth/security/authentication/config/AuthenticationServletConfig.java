package vin.pth.security.authentication.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vin.pth.security.authentication.handler.sevlet.AuthenticationFailureHandler;
import vin.pth.security.authentication.handler.sevlet.AuthenticationSuccessHandler;
import vin.pth.security.core.context.UserAuthServletHolder;
import vin.pth.session.core.context.ServletSessionHolder;

/**
 * @author Cocoon
 * @date 2022/11/3
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
public class AuthenticationServletConfig {

  @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
  @ConditionalOnWebApplication(type = Type.SERVLET)
  @Bean
  public AuthenticationFailureHandler authenticationFailureHandler() {
    return (request, response, e) -> {

      log.error("授权失败：", e);
      response.setStatus(401);
      try {
        response.getWriter().write(new String(e.getMessage().getBytes(StandardCharsets.UTF_8)));
      } catch (IOException ex) {
        response.setStatus(500);
      }
    };
  }

  @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
  @ConditionalOnWebApplication(type = Type.SERVLET)
  @Bean
  public AuthenticationSuccessHandler servletAuthenticationSuccessHandler() {
    return (request, response, authInfo) -> {
      try {
        response.setStatus(200);
        UserAuthServletHolder.setUserAuthInfo(authInfo);
        response.getWriter().write(ServletSessionHolder.getSession().getSessionId());
      } catch (IOException e) {
        response.setStatus(401);
      }
    };
  }


}
