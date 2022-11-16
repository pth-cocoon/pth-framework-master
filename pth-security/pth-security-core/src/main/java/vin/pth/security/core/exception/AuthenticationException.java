package vin.pth.security.core.exception;

import lombok.Getter;

/**
 * @author Cocoon
 * @date 2022/11/4
 */
public class AuthenticationException extends RuntimeException {

  @Getter
  private final int code;


  public AuthenticationException(int code, String message) {
    super(message);
    this.code = code;
  }
}
