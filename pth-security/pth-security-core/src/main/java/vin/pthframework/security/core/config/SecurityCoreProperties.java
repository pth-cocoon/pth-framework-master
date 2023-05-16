package vin.pthframework.security.core.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Cocoon
 */
@ConfigurationProperties(prefix = "pth.security.core")
@Data
public class SecurityCoreProperties {

  /**
   * 开启权限拦截
   */
  private boolean enableAuthorization = true;

  /**
   * whiteList.
   */
  private List<String> whiteList = new ArrayList<>();


}
