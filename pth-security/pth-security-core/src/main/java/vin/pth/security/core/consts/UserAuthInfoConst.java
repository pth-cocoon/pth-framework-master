package vin.pth.security.core.consts;

import vin.pth.security.core.pojo.PthAuthority;
import vin.pth.security.core.pojo.UserAuthInfo;

import java.util.ArrayList;

/**
 * @author Cocoon
 */
public class UserAuthInfoConst {

  public static final UserAuthInfo ADMIN_USER = new UserAuthInfo();

  static {
    ADMIN_USER.setId(1L);
    ADMIN_USER.setUsername("admin");
    ADMIN_USER.setUsername("admin");
    var authorities = new ArrayList<PthAuthority>();
    authorities.add(new PthAuthority("*", "/**"));
    ADMIN_USER.setAuthorities(authorities);
  }

  private UserAuthInfoConst() {
  }


}
