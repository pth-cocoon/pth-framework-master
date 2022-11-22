package vin.pth.session.servlet.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vin.pth.security.core.context.UserAuthServletHolder;
import vin.pth.security.core.model.UserAuthInfo;

/**
 * @author Cocoon
 * @date 2022/11/16
 */
@RequestMapping("userInfo")
@RestController
public class UserInfoController {

  @GetMapping("get")
  public UserAuthInfo userAuthInfo() {
    return UserAuthServletHolder.getUserAuthInfo().orElse(null);
  }

}
