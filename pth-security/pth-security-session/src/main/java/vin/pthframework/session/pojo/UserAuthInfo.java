package vin.pthframework.session.pojo;

import java.io.Serializable;
import java.util.Collection;
import lombok.Data;

/**
 * @author Cocoon
 */
@Data
public class UserAuthInfo implements Serializable {

  private Long id;
  private String username;
  private String password;
  private Collection<PthAuthority> authorities;
  private Collection<String> roles;
  private Serializable extraData;
  private String sessionId;
}
