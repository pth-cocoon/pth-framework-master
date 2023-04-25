package vin.pth.session.reactive.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;
import vin.pth.security.core.consts.UserAuthInfoConst;
import vin.pth.security.core.pojo.UserAuthInfo;
import vin.pth.security.reactive.util.LoginUtil;

/**
 * @author Cocoon
 * @date 2022/11/13
 */
@Slf4j
@RequestMapping("session")
@RestController
public class SessionController {

  @GetMapping("put")
  public Mono<String> test(WebSession session) {
    session.getAttributes().put("test", System.currentTimeMillis() + "webflux");
    LoginUtil.login(UserAuthInfoConst.ADMIN_USER, session);
    return Mono.just(session.getId());
  }

  @GetMapping("get")
  public Mono<UserAuthInfo> get(WebSession session) {
    log.info(session.getId());
    UserAuthInfo authInfo = LoginUtil.getAuthInfo(session);
    authInfo.setSessionId(session.getId());
    return Mono.just(authInfo);
  }


}
