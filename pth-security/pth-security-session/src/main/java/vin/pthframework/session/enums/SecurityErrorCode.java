package vin.pthframework.session.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Cocoon
 */
@RequiredArgsConstructor
@Getter
public enum SecurityErrorCode {
  /**
   * 未登录.
   */
  NOT_LOGIN(401001, "登录后重试"),

  ;
  private final int code;
  private final String message;
}
