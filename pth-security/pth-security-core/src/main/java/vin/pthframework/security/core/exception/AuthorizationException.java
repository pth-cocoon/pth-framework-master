package vin.pthframework.security.core.exception;


import vin.pthframework.security.core.enums.ErrorCode;

/**
 * @author Cocoon
 */
public class AuthorizationException extends BaseSecurityException {

  public AuthorizationException(ErrorCode errorCode) {
    super(errorCode);
  }

  public AuthorizationException(int code, String message) {
    super(code, message);
  }
}
