package vin.pthframework.security.servlet.repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import vin.pthframework.security.servlet.util.LoginUtil;
import vin.pthframework.security.servlet.util.UserAuthInfoHolder;
import vin.pthframework.session.consts.SecurityConst;
import vin.pthframework.session.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
public class ServletSecurityContextRepository {

  public void saveContext(HttpServletRequest request) {
    HttpSession session = request.getSession();
    UserAuthInfo userAuthInfo = LoginUtil.getAuthInfo(session);
    if (userAuthInfo == null) {
      session.removeAttribute(SecurityConst.USER_INFO_KEY);
    } else {
      UserAuthInfoHolder.setUserAuthInfo(userAuthInfo);
    }
  }


}
