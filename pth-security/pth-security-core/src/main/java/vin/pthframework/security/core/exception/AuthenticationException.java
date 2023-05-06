package vin.pthframework.security.core.exception;


import vin.pthframework.security.core.enums.ErrorCode;

/**
 * @author Cocoon
 */
public class AuthenticationException extends BaseSecurityException {

  public AuthenticationException(ErrorCode errorCode) {
    super(errorCode);
  }
}
