package vin.pth.security.core.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Cocoon
 * @date 2022/11/4
 */
@Getter
@ToString
public class UserAuthInfo implements Serializable {

  private final String userId;

  @Setter
  private String token;

  @Setter
  private Collection<String> roles;

  @Setter
  private Collection<Authority> authorities;
  private final Map<String, Object> extraInfo = new HashMap<>();

  private UserAuthInfo() {
    this.userId = "";
  }

  public UserAuthInfo(String userId) {
    this.userId = userId;
  }

  public void putExtraInfo(String key, Object data) {
    extraInfo.put(key, data);
  }

  public Object getExtraInfo(String key) {
    return extraInfo.get(key);
  }

}