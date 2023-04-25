package vin.pth.security.core.util;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vin.pth.security.core.pojo.PthAuthority;
import vin.pth.security.core.pojo.UserAuthInfo;

import java.util.Collection;

/**
 * @author Cocoon
 */
public class RbacChecker {

  private static final String ALL = "*";
  private static final AntPathMatcher MATCHER = new AntPathMatcher();

  private RbacChecker() {
  }

  public static void check(UserAuthInfo authInfo, String method, String uri) {
    SecurityAccessAssert.checkUser(authInfo);
    if (checkPermission(authInfo.getAuthorities(), method, uri)) {
      return;
    }
    throw new AccessDeniedException("没有权限");

  }

  private static boolean checkPermission(Collection<PthAuthority> authorities, String method, String uri) {
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

  private static boolean checkMethod(String ruleMethod, String targetMethod) {
    if (!StringUtils.hasText(ruleMethod) || ALL.equals(ruleMethod)) {
      return true;
    }
    return ruleMethod.equalsIgnoreCase(targetMethod);

  }
}
