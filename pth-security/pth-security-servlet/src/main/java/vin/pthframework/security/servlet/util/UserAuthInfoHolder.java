package vin.pthframework.security.servlet.util;


import vin.pthframework.session.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
public class UserAuthInfoHolder {

  private static final ThreadLocal<UserAuthInfo> AUTH_INFO_THREAD_LOCAL = new ThreadLocal<>();

  public static UserAuthInfo getUserAuthInfo() {
    return AUTH_INFO_THREAD_LOCAL.get();
  }

  public static void setUserAuthInfo(UserAuthInfo userAuthInfo) {
    AUTH_INFO_THREAD_LOCAL.set(userAuthInfo);
  }

  public static void clear() {
    AUTH_INFO_THREAD_LOCAL.remove();
  }

}
