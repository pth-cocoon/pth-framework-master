package vin.pthframework.security.core.consts;


import java.util.ArrayList;
import vin.pthframework.session.pojo.PthAuthority;
import vin.pthframework.session.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
public class UserAuthInfoConst {

  public static final UserAuthInfo ADMIN_USER = new UserAuthInfo();
  public static final UserAuthInfo ANONYMOUS = new UserAuthInfo();

  static {
    ADMIN_USER.setId(-1L);
    ADMIN_USER.setUsername("admin");
    var authorities = new ArrayList<PthAuthority>();
    authorities.add(new PthAuthority("*", "/**"));
    ADMIN_USER.setAuthorities(authorities);

    ANONYMOUS.setId(-2L);
    ANONYMOUS.setUsername("anonymous");
    ANONYMOUS.setAuthorities(new ArrayList<>());
  }

  private UserAuthInfoConst() {
  }


}
