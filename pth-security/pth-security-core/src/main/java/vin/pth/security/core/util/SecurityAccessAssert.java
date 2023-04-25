package vin.pth.security.core.util;

import org.springframework.security.access.AccessDeniedException;
import vin.pth.security.core.pojo.UserAuthInfo;


/**
 * @author Cocoon
 */
public class SecurityAccessAssert {

  private SecurityAccessAssert() {
  }

  public static void checkUser(UserAuthInfo o) {
    if (o == null) {
      throw new AccessDeniedException("登陆后重试");
    }
  }
}
