package vin.pthframework.security.servlet.handler;

import javax.servlet.http.HttpServletResponse;
import vin.pthframework.security.core.exception.BaseSecurityException;

/**
 * @author Cocoon
 */
public interface AuthorizationFailureHandler {

  /**
   * 鉴权失败处理器
   *
   * @param response HttpServletResponse
   * @param e        权限异常
   */
  void failureHandler(HttpServletResponse response, BaseSecurityException e);


}
