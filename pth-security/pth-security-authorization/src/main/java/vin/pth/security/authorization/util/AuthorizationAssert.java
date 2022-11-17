package vin.pth.security.authorization.util;

import vin.pth.security.core.exception.AuthorizationException;

/**
 * @author Cocoon
 * @date 2022/11/17
 */
public class AuthorizationAssert {

  public static void isTrue(boolean expression, String message) {
    if (!expression) {
      throw new AuthorizationException(403, message);
    }
  }

}
