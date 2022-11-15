package vin.pth.session.core.config;

import java.io.Serializable;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import vin.pth.session.core.enums.HttpPosition;
import vin.pth.session.core.enums.SessionStoreType;

/**
 * @author Cocoon
 */
@ConfigurationProperties(prefix = "pth.session")
@Data
public class PthSessionProperties implements Serializable {


  /**
   * enable cocoon session.
   */
  private boolean enable = true;

  private HttpPosition requestPosition = HttpPosition.COOKIE;

  /**
   * SessionIdKey.
   */
  private String sessionIdKey = "session-id";

  /**
   * tokenKey
   */
  private String tokenKey = "cocoon-token";

  /**
   * SessionTimeOut. Session 过期时间.
   */
  @DurationUnit(ChronoUnit.SECONDS)
  private Duration timeout = Duration.ofMinutes(60);

  /**
   * auto commit session. 开启自动提交Session
   */
  private boolean enableAutoCommit = true;

  /**
   * session存储方式.
   */
  private SessionStoreType storeType = SessionStoreType.MEMORY;

}
