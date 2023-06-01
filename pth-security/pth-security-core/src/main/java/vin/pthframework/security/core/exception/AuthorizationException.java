package vin.pthframework.security.core.exception;


import vin.pthframework.security.core.enums.ErrorCode;

/**
 * @author Cocoon
 */
public class AuthorizationException extends BaseSecurityException {

  public AuthorizationException(ErrorCode errorCode) {
    super(errorCode);
  }

  public AuthorizationException(ErrorCode errorCode, String message) {
    super(errorCode.getCode(), message);
  }
}
