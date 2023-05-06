package vin.pthframework.security.servlet.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import vin.pthframework.security.core.util.ResultUtil;
import vin.pthframework.security.servlet.handler.AuthorizationFailureHandler;

/**
 * @author Cocoon
 */
@Slf4j
@Configuration
public class SecurityBeanConfig {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @ConditionalOnMissingBean(AuthorizationFailureHandler.class)
  @Bean
  public AuthorizationFailureHandler authorizationFailureHandler() {
    return (response, e) -> {
      var code = e.getCode() + "";
      if (code.startsWith("401")) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
      } else if (code.startsWith("403")) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
      }
      response.setContentType(MediaType.APPLICATION_JSON.getType());
      Map<String, Object> resultMap = ResultUtil.error(e);
      try {
        response.getWriter().println(toResultStr(resultMap));
        response.flushBuffer();
      } catch (IOException ex) {
        log.error("e", ex);
        throw new RuntimeException(ex);
      }
    };
  }

  private String toResultStr(Object o) {
    try {
      return objectMapper.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      log.error("e", e);
      return "";
    }
  }


}
