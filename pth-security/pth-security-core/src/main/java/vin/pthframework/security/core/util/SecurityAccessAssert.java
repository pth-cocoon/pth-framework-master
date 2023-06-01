package vin.pthframework.security.core.util;

import vin.pthframework.security.core.enums.SecurityErrorCode;
import vin.pthframework.security.core.exception.AuthorizationException;
import vin.pthframework.session.pojo.UserAuthInfo;


/**
 * @author Cocoon
 */
public class SecurityAccessAssert {

  private SecurityAccessAssert() {
  }

  public static void checkUser(UserAuthInfo o) {
    if (o == null) {
      throw new AuthorizationException(SecurityErrorCode.CODE403);
    }
  }

  public static void checkCredentials(boolean expression, String message) {
    if (!expression) {
      throw new AuthorizationException(SecurityErrorCode.INVALID_CREDENTIALS, message);
    }
  }
}
