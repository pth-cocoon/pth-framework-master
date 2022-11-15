package vin.pth.session.core.context;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;

/**
 * @author Cocoon
 */
@Getter
public class PthSession implements Serializable {

  private String sessionId;
  private long createTime;
  private Map<String, Object> sessionData;

  private PthSession() {
  }

  public PthSession(String sessionId) {
    this.sessionId = sessionId;
    this.createTime = System.currentTimeMillis();
    this.sessionData = new ConcurrentHashMap<>();
  }

}
