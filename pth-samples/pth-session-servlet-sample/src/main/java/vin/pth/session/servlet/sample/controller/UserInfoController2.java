package vin.pth.session.servlet.sample.controller;

import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vin.pth.security.core.context.UserAuthServletHolder;
import vin.pth.security.core.model.UserAuthInfo;

/**
 * @author Cocoon
 * @date 2022/11/16
 */
@RequestMapping("userInfo2")
@RestController
public class UserInfoController2 {

  @GetMapping("get")
  public UserAuthInfo userAuthInfo() {
    Optional<UserAuthInfo> optionalUser = UserAuthServletHolder.getUserAuthInfo();
    return optionalUser.orElse(null);
  }

}
