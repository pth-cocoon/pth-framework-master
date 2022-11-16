package vin.pth.security.authentication.config;

import java.io.IOException;
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
@Configuration

@ConditionalOnWebApplication(type = Type.SERVLET)
public class AuthenticationServletConfig {

  @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
  @ConditionalOnWebApplication(type = Type.SERVLET)
  @Bean
  public AuthenticationFailureHandler authenticationFailureHandler() {
    return (request, response, e) -> {
      response.setStatus(401);
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
