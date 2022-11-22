package vin.pth.security.core.context;

import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;
import vin.pth.security.core.constant.SecurityCoreConstant;
import vin.pth.security.core.model.UserAuthInfo;
import vin.pth.session.core.context.PthSession;
import vin.pth.session.core.context.ReactiveSessionHolder;

/**
 * @author Cocoon
 * @date 2022/11/5
 */
@SuppressWarnings("unused")
public class UserAuthReactiveHolder {

  public static Mono<PthSession> setUserAuthInfo(@NonNull UserAuthInfo userAuthInfo) {
    return ReactiveSessionHolder.getSession().map(session -> {
      Assert.notNull(session, "session is null");
      session.getSessionData().put(SecurityCoreConstant.USER_AUTH_INFO_KEY, userAuthInfo);
      return session;
    });
  }

  public static Mono<Optional<UserAuthInfo>> getUserAuthInfo() {
    return ReactiveSessionHolder.getSession().map(session -> {
      Assert.notNull(session, "session is null");
      UserAuthInfo userAuthInfo =
          (UserAuthInfo) session.getSessionData().get(SecurityCoreConstant.USER_AUTH_INFO_KEY);
      if (userAuthInfo != null) {
        return Optional.of(userAuthInfo);
      }
      return Optional.empty();
    });
  }

  public static Mono<Void> clear() {
    return ReactiveSessionHolder.getSession().map(session -> {
      Assert.notNull(session, "session is null");
      session.getSessionData().remove(SecurityCoreConstant.USER_AUTH_INFO_KEY);
      return session;
    }).then();
  }

}
