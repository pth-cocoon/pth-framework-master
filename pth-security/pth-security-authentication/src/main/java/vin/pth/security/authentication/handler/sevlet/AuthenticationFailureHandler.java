package vin.pth.security.authentication.handler.sevlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vin.pth.security.core.exception.AuthenticationException;

/**
 * @author Cocoon
 * @date 2022/11/4
 */
public interface AuthenticationFailureHandler {

  /**
   * 登陆成功处理.
   *
   * @param request  Request
   * @param response Response
   * @param e        权限认证异常.
   */
  void failureHandler(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException e);

}
