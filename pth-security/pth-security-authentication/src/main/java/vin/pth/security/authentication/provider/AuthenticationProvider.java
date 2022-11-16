package vin.pth.security.authentication.provider;

import java.util.Map;
import vin.pth.security.core.model.UserAuthInfo;

/**
 * @author Cocoon
 * @date 2022/11/4
 */
public interface AuthenticationProvider<T> {

  /**
   * 认证。
   *
   * @param param T
   * @return PthRequest
   */
  UserAuthInfo authenticate(T param);

  /**
   * 当前provider是否支持此Request的登陆.
   *
   * @param param Map.
   * @return true=支持.
   */
  T supports(Map<String, Object> param);
}
