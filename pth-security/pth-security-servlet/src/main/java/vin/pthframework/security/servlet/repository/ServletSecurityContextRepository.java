package vin.pthframework.security.servlet.repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import vin.pthframework.security.core.consts.SecurityConst;
import vin.pthframework.security.core.context.SecurityContext;
import vin.pthframework.security.servlet.util.LoginUtil;
import vin.pthframework.security.servlet.util.SecurityContextHolder;
import vin.pthframework.session.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
public class ServletSecurityContextRepository {

  public void saveContext(HttpServletRequest request) {
    HttpSession session = request.getSession();
    UserAuthInfo userAuthInfo = LoginUtil.getAuthInfo(session);
    var context = new SecurityContext();
    if (userAuthInfo == null) {
      session.removeAttribute(SecurityConst.USER_INFO_KEY);
    } else {
      context.setAuthInfo(userAuthInfo);
    }
    SecurityContextHolder.setContext(context);
  }


  public SecurityContext loadContext(HttpServletRequest request) {
    return SecurityContextHolder.getContext();
  }


}
