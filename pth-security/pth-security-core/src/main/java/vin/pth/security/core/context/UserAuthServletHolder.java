package vin.pth.security.core.context;

import java.util.Optional;
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

  public static Optional<UserAuthInfo> getUserAuthInfo() {
    PthSession session = ServletSessionHolder.getSession();
    Assert.notNull(session, "Session is null");
    UserAuthInfo userAuthInfo =
        (UserAuthInfo) session.getSessionData().get(SecurityCoreConstant.USER_AUTH_INFO_KEY);
    if (userAuthInfo != null) {
      return Optional.of(userAuthInfo);
    }
    return Optional.empty();
  }

  @SuppressWarnings("unused")
  public static void clear() {
    PthSession session = ServletSessionHolder.getSession();
    Assert.notNull(session, "Session is null");
    session.getSessionData().remove(SecurityCoreConstant.USER_AUTH_INFO_KEY);

  }

}
