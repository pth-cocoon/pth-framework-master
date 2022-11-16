package vin.pth.security.authorization.component;

import vin.pth.security.core.model.UserAuthInfo;

/**
 * @author Cocoon
 * @date 2022/11/15
 */
public interface RbacChecker {

  /**
   * 校验用户权限.
   *
   * @param method       HttpMethod
   * @param uri          请求路径
   * @param userAuthInfo 用户权限信息.
   * @return true=pass
   */
  boolean check(String method, String uri, UserAuthInfo userAuthInfo);

}
