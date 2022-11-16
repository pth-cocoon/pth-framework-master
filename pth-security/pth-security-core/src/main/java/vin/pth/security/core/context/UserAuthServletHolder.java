package vin.pth.security.core.context;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import vin.pth.security.core.constant.SecurityCoreConstant;
import vin.pth.security.core.model.UserAuthInfo;
import vin.pth.session.core.context.PthSession;
import vin.pth.session.core.context.ServletSessionHolder;

/**
 * @author Cocoon
 * @date 2022/11/5
 */
public class UserAuthServletHolder {


  public static void setUserAuthInfo(@NonNull UserAuthInfo userAuthInfo) {
    PthSession session = ServletSessionHolder.getSession();
    Assert.notNull(session, "Session is null");
    session.getSessionData().put(SecurityCoreConstant.USER_AUTH_INFO_KEY, userAuthInfo);
  }

  public static UserAuthInfo getUserAuthInfo() {
    PthSession session = ServletSessionHolder.getSession();
    Assert.notNull(session, "Session is null");
    return (UserAuthInfo) session.getSessionData().get(SecurityCoreConstant.USER_AUTH_INFO_KEY);
  }

  public static void clear() {
    PthSession session = ServletSessionHolder.getSession();
    Assert.notNull(session, "Session is null");
    session.getSessionData().remove(SecurityCoreConstant.USER_AUTH_INFO_KEY);

  }

}
