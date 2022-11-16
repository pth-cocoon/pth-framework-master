package vin.pth.security.authentication.handler.sevlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vin.pth.security.core.model.UserAuthInfo;

/**
 * @author Cocoon
 * @date 2022/11/4
 */
public interface AuthenticationSuccessHandler {

  /**
   * 登陆成功处理.
   *
   * @param request      Request
   * @param response     Response
   * @param userAuthInfo 用户权限信息
   */
  void successHandler(HttpServletRequest request, HttpServletResponse response,
      UserAuthInfo userAuthInfo);

}
