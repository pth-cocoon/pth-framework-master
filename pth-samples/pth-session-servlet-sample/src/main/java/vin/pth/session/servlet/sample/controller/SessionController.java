package vin.pth.session.servlet.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vin.pth.security.core.consts.UserAuthInfoConst;
import vin.pth.security.core.pojo.UserAuthInfo;
import vin.pth.security.servlet.util.LoginUtil;

import javax.servlet.http.HttpSession;

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
    LoginUtil.login(UserAuthInfoConst.ADMIN_USER, session);
    return session.getId();
  }

  @GetMapping("get")
  public UserAuthInfo get(HttpSession session) {
    log.info(session.getId());
    UserAuthInfo authInfo = LoginUtil.getAuthInfo(session);
    authInfo.setSessionId(session.getId());
    return authInfo;
  }


}
