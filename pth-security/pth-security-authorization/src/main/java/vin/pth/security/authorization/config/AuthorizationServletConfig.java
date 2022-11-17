package vin.pth.security.authorization.config;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vin.pth.security.authorization.handler.servlet.AuthorizationFailureHandler;
import vin.pth.security.core.exception.AuthorizationException;

/**
 * @author Cocoon
 * @date 2022/11/16
 */
@ConditionalOnWebApplication(type = Type.SERVLET)
@Configuration
public class AuthorizationServletConfig {

  @Bean
  public AuthorizationFailureHandler authorizationFailureHandler() {
    return new AuthorizationFailureHandler() {
      @Override
      public void failureHandler(HttpServletResponse response, AuthorizationException e) {
        response.setStatus(403);
        response.setCharacterEncoding("UTF-8");
        try {
          response.getWriter().write(e.getMessage());
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      }
    };
  }

}
