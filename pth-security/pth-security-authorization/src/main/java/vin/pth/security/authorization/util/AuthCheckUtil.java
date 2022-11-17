package vin.pth.security.authorization.util;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import vin.pth.security.core.model.Authority;

/**
 * @author Cocoon
 * @date 2022/11/17
 */
@Slf4j
public class AuthCheckUtil {

  private final static PathMatcher PATH_MATCHER = new AntPathMatcher();

  public static boolean checkList(String method, String uri, Collection<Authority> whiteList) {
    for (Authority authority : whiteList) {
      if (PATH_MATCHER.match(authority.uri(), uri)) {
        if (authority.method() == null || authority.method().name().equalsIgnoreCase(method)) {
          log.info("URI:{},是白名单:{}", uri, authority.uri());
          return true;
        }
      }
    }
    return false;
  }

}
