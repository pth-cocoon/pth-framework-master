package vin.pth.security.authentication.filter.react;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vin.pth.security.authentication.handler.reactive.AuthenticationFailureHandler;
import vin.pth.security.authentication.handler.reactive.AuthenticationSuccessHandler;
import vin.pth.security.authentication.provider.ProviderManager;
import vin.pth.security.core.exception.AuthenticationException;
import vin.pth.security.core.model.UserAuthInfo;

/**
 * @author Cocoon
 * @date 2022/11/3
 */
@Slf4j
@ConditionalOnWebApplication(type = Type.REACTIVE)
@Component
@RequiredArgsConstructor
public class LoginReactiveFilter implements WebFilter {

  private final AuthenticationSuccessHandler authenticationSuccessHandler;
  private final AuthenticationFailureHandler authenticationFailureHandler;
  private final ProviderManager providerManager;

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    if (!support(exchange.getRequest())) {
      return chain.filter(exchange);
    }
    try {
      UserAuthInfo authenticate = providerManager.authenticate(getBody(exchange.getRequest()));
      return authenticationSuccessHandler.successHandler(exchange, authenticate);
    } catch (AuthenticationException e) {
      return authenticationFailureHandler.failureHandler(exchange, e);
    }
  }

  private Map<String, Object> getBody(ServerHttpRequest serverHttpRequest) {
    Flux<DataBuffer> body = serverHttpRequest.getBody();
    StringBuilder sb = new StringBuilder();
    body.subscribe(buffer -> {
      byte[] bytes = new byte[buffer.readableByteCount()];
      buffer.read(bytes);
      String bodyString = new String(bytes, StandardCharsets.UTF_8);
      sb.append(bodyString);
    });
    try {
      return OBJECT_MAPPER.readValue(sb.toString(), new TypeReference<>() {
      });
    } catch (JsonProcessingException e) {
      log.error("e", e);
      return Collections.emptyMap();
    }
  }


  private boolean support(ServerHttpRequest servletRequest) {
    var uri = servletRequest.getURI().getPath();
    return "/login".equalsIgnoreCase(uri) && servletRequest.getMethod()
        .name().equalsIgnoreCase(HttpMethod.POST.name());
  }


}
