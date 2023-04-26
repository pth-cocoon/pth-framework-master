package vin.pth.session.servlet.sample.controller;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    session.setAttribute("123", "123");
    return session.getId();
  }

  @GetMapping("get")
  public Object get(HttpSession session) {
    log.info(session.getId());
    return session.getAttribute("123");
  }


}
