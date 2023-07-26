package vin.pthframework.security.reactive.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import vin.pthframework.security.core.exception.BaseSecurityException;
import vin.pthframework.security.core.util.ResultUtil;
import vin.pthframework.security.reactive.handler.AuthorizationFailureHandler;

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
      String codeStr = (String.valueOf(e.getCode())).substring(0, 3);
      switch (Integer.parseInt(codeStr)) {
        case 401 -> response.setStatusCode(HttpStatus.UNAUTHORIZED);
        case 403 -> response.setStatusCode(HttpStatus.FORBIDDEN);
        default -> {
          log.error("未处理的异常", e);
          response.setStatusCode(HttpStatus.FORBIDDEN);
        }
      }
      DataBufferFactory bufferFactory = response.bufferFactory();
      Map<String, Object> resultMap = ResultUtil.error(e);
      DataBuffer wrap =
          bufferFactory.wrap(toResultStr(resultMap).getBytes(StandardCharsets.UTF_8));
      return response.writeWith(Mono.fromSupplier(() -> wrap));
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
