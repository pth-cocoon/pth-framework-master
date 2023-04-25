package vin.pth.security.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import vin.pth.security.core.enums.HttpPosition;

import java.util.ArrayList;
import java.util.List;

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
   * SessionPosition
   */
  private HttpPosition sessionPosition = HttpPosition.COOKIE;

  /**
   * SessionKey
   */
  private String sessionKey = "session-id";

  /**
   * whiteList.
   */
  private List<String> whiteList = new ArrayList<>();


}
