package vin.pthframework.security.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Cocoon
 */
@RequiredArgsConstructor
@Getter
public enum SecurityErrorCode implements ErrorCode {
  /**
   * 未登录.
   */
  NOT_LOGIN(401001, "登录后重试"),
  CODE403(403001, "没有权限"),
  INVALID_CREDENTIALS(401002, "凭证无效"),

  ;
  private final int code;
  private final String message;
}
