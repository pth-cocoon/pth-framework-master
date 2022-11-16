package vin.pth.security.authentication.handler.reactive;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import vin.pth.security.core.model.UserAuthInfo;

/**
 * @author Cocoon
 * @date 2022/11/4
 */
public interface AuthenticationSuccessHandler {

  /**
   * 登陆成功处理.
   *
   * @param response     Response
   * @param userAuthInfo 用户权限信息
   * @return Mono
   */
  Mono<Void> successHandler(ServerWebExchange response, UserAuthInfo userAuthInfo);

}
