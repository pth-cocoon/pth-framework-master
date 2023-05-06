package vin.pthframework.security.core.factory;

import vin.pthframework.security.core.enums.ErrorCode;
import vin.pthframework.security.core.exception.AuthenticationException;
import vin.pthframework.security.core.exception.AuthorizationException;
import vin.pthframework.security.core.exception.BaseSecurityException;

/**
 * @author Cocoon
 */
public class SecurityExceptionFactory {

  private final static String CODE401 = "401";
  private final static String CODE403 = "403";

  public static BaseSecurityException getByErrorCode(ErrorCode errorCode) {
    var codeStr = errorCode.getCode() + "";
    if (codeStr.startsWith(CODE401)) {
      return new AuthenticationException(errorCode);
    }
    if (codeStr.startsWith(CODE403)) {
      return new AuthorizationException(errorCode);
    }
    throw new RuntimeException("未知的ErrorCode");
  }


}
