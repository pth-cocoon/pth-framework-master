package vin.pthframework.session.servlet.sample.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vin.pthframework.security.core.consts.UserAuthInfoConst;
import vin.pthframework.security.servlet.util.LoginUtil;
import vin.pthframework.security.servlet.util.UserAuthInfoHolder;

/**
 * @author Cocoon
 * @date 2022/11/13
 */
@Slf4j
@RequestMapping("session")
@RestController
public class SessionController {

  @GetMapping("put")
  public String test(HttpSession session) {
    LoginUtil.login(UserAuthInfoConst.ANONYMOUS);
    return session.getId();
  }

  @GetMapping("get")
  public Object get(HttpSession session) {
    log.info(session.getId());
    if (UserAuthInfoHolder.getUserAuthInfo() == null) {
      return null;
    }
    return UserAuthInfoHolder.getUserAuthInfo();
  }


}
