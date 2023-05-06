package vin.pthframework.security.core.exception;

import lombok.Getter;
import vin.pthframework.security.core.enums.ErrorCode;

/**
 * @author Cocoon
 */
public abstract class BaseSecurityException extends RuntimeException {

  @Getter
  private final int code;


  private BaseSecurityException(int code, String message) {
    super(message);
    this.code = code;
  }

  protected BaseSecurityException(ErrorCode errorCode) {
    this(errorCode.getCode(), errorCode.getMessage());
  }
}
