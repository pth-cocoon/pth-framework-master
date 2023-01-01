package vin.pth.session.servlet.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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

  @PostMapping("import")
  public String upload(@RequestParam("file") MultipartFile file) {
    System.out.println(file.getName());
    return "success";
  }


}
