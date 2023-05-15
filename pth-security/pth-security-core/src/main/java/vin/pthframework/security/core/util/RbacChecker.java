package vin.pthframework.security.core.util;

import java.util.Collection;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vin.pthframework.security.core.enums.SecurityErrorCode;
import vin.pthframework.security.core.exception.BaseSecurityException;
import vin.pthframework.security.core.factory.SecurityExceptionFactory;
import vin.pthframework.session.pojo.PthAuthority;
import vin.pthframework.session.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
public class RbacChecker {

  private static final String ALL = "*";
  private static final AntPathMatcher MATCHER = new AntPathMatcher();

  private RbacChecker() {
  }

  public static void check(UserAuthInfo authInfo, String method, String uri)
      throws BaseSecurityException {
    if (authInfo == null) {
      throw SecurityExceptionFactory.getByErrorCode(SecurityErrorCode.NOT_LOGIN);
    }
    SecurityAccessAssert.checkUser(authInfo);
    if (checkPermission(authInfo.getAuthorities(), method, uri)) {
      return;
    }
    throw SecurityExceptionFactory.getByErrorCode(SecurityErrorCode.CODE403);

  }

  private static boolean checkPermission(Collection<PthAuthority> authorities, String method,
      String uri) {
    if (CollectionUtils.isEmpty(authorities)) {
      return false;
    }
    for (PthAuthority authority : authorities) {
      if (MATCHER.match(authority.uri(), uri) && checkMethod(authority.method(), method)) {
        return true;
      }
    }
    return false;
  }

  public static boolean checkWhiteList(Collection<String> whiteList, String uri) {
    for (String s : whiteList) {

      if (MATCHER.match(s, uri)) {
        return true;
      }
    }
    return false;

  }

  private static boolean checkMethod(String ruleMethod, String targetMethod) {
    if (!StringUtils.hasText(ruleMethod) || ALL.equals(ruleMethod)) {
      return true;
    }
    return ruleMethod.equalsIgnoreCase(targetMethod);

  }
}
