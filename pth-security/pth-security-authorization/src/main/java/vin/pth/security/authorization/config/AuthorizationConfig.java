package vin.pth.security.authorization.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import vin.pth.security.authorization.component.RbacChecker;
import vin.pth.security.authorization.util.AuthCheckUtil;

/**
 * @author Cocoon
 * @date 2022/11/15
 */
@Configuration
@ComponentScan("vin.pth.security.authorization.**")
@ImportAutoConfiguration(AuthorizationServletConfig.class)
@EnableConfigurationProperties(AuthorizationProperties.class)
public class AuthorizationConfig {

  @Bean
  public RbacChecker rbacChecker() {
    return (method, uri, userAuthInfo) -> AuthCheckUtil.checkList(method, uri,
        userAuthInfo.getAuthorities());
  }


}
