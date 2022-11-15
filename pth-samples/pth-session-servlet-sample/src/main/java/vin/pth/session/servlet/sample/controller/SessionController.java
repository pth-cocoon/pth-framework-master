package vin.pth.session.servlet.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vin.pth.session.core.context.PthSession;
import vin.pth.session.core.context.ServletSessionHolder;

/**
 * @author Cocoon
 * @date 2022/11/13
 */
@RequestMapping("session")
@RestController
public class SessionController {

  @GetMapping("test")
  public PthSession get() {
    PthSession session = ServletSessionHolder.getSession();
    session.getSessionData().put("test", System.currentTimeMillis());
    session.getSessionData().put("Servlet", "servlet");
    return ServletSessionHolder.getSession();
  }

}
