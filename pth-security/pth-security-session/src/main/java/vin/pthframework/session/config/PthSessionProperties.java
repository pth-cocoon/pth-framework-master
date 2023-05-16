package vin.pthframework.session.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import vin.pthframework.session.enums.HttpPosition;
import vin.pthframework.session.enums.SessionStoreType;

/**
 * @author Cocoon
 */
@ConfigurationProperties(prefix = "pth.session.core")
@Data
public class PthSessionProperties {

  /**
   * SessionPosition
   */
  private HttpPosition sessionPosition = HttpPosition.COOKIE;

  /**
   * SessionKey
   */
  private String sessionKey = "session-id";
  /**
   * StoreType 暂时废弃.
   */
  @Deprecated
  private SessionStoreType storeType = SessionStoreType.MEMORY;


}