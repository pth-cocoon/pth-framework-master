package vin.pth.security.authentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author Cocoon
 * @date 2022/11/4
 */
@ConfigurationProperties("pth.security.authentication")
@Validated
@Data
public class AuthenticationProperties {

  /**
   * 登陆路径.
   */
  private String loginUri = "/login";

}
