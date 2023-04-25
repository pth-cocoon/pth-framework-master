package vin.pth.security.servlet.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import vin.pth.security.core.enums.SecurityErrorCode;
import vin.pth.security.core.util.ResultUtil;

import java.util.Map;

/**
 * @author Cocoon
 */
@Slf4j
@Configuration
public class SecurityBeanConfig {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @ConditionalOnMissingBean(AccessDeniedHandler.class)
  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    log.warn("默认的鉴权失败处理器，根据业务需要覆盖Bean......");
    return (request, response, accessDeniedException) -> {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.setContentType(MediaType.APPLICATION_JSON.getType());
      Map<String, Object> resultMap = ResultUtil.error(SecurityErrorCode.NOT_LOGIN);
      response.getWriter().println(toResultStr(resultMap));
      response.flushBuffer();
    };

  }

  @ConditionalOnMissingBean(AuthenticationEntryPoint.class)
  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    log.warn("默认的未登录拦截，根据业务需要覆盖Bean......");
    return (request, response, authException) -> {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType(MediaType.APPLICATION_JSON.getType());
      Map<String, Object> resultMap = ResultUtil.error(SecurityErrorCode.NOT_LOGIN);
      response.getWriter().println(toResultStr(resultMap));
      response.flushBuffer();
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
