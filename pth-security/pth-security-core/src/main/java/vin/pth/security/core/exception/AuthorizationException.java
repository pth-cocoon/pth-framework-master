package vin.pth.security.core.exception;

import lombok.Getter;

/**
 * @author Cocoon
 * @date 2022/11/4
 */
public class AuthorizationException extends RuntimeException {

  @Getter
  private final int code;


  public AuthorizationException(int code, String message) {
    super(message);
    this.code = code;
  }
}
