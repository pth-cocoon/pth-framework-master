package vin.pthframework.base.dao.mbp.config;

import javax.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vin.pthframework.base.dao.mbp.handler.CurrentUserService;
import vin.pthframework.base.dao.mbp.handler.MbpOperatorHandler;

/**
 * @author Cocoon
 */
@Configuration
public class MbpDefaultConfig {


  @PostConstruct
  public void init() {
    System.setProperty("pagehelper.banner", "false");
  }

  @ConditionalOnBean(CurrentUserService.class)
  @Bean
  public MbpOperatorHandler mbpOperatorHandler(CurrentUserService currentUserService) {
    return new MbpOperatorHandler(currentUserService);
  }

}
