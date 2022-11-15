package vin.pth.session.data.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Cocoon
 * @date 2022/11/14
 */
@ConfigurationProperties(prefix = "pth.session.redis")
@Getter
@Setter
public class PthRedisSessionProperties {

  /**
   * key命名空间.
   */
  private String namespace = "pth:session";

}
