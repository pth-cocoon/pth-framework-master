package vin.pth.session.reactive.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import vin.pth.session.core.context.PthSession;
import vin.pth.session.core.context.ReactiveSessionHolder;

/**
 * @author Cocoon
 * @date 2022/11/13
 */
@RequestMapping("session")
@RestController
public class SessionController {

  @GetMapping("test")
  public Mono<PthSession> get() {
    return ReactiveSessionHolder.getSession().flatMap(session -> {
      session.getSessionData().put("test", System.currentTimeMillis());
      session.getSessionData().put("reactive", "reactive");
      return Mono.just(session);
    });
  }

}
