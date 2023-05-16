package vin.pthframework.security.reactive.util;

import java.util.function.Function;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import vin.pthframework.session.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
public final class ReactiveSecurityContextHolder {

  private static final Class<?> AUTH_INFO_CLASS = UserAuthInfo.class;

  private ReactiveSecurityContextHolder() {
  }

  public static Mono<UserAuthInfo> getContext() {
    return Mono.subscriberContext()
        .filter(ReactiveSecurityContextHolder::hasUserAuthInfo)
        .flatMap(ReactiveSecurityContextHolder::getUserAuthInfo);
  }

  private static boolean hasUserAuthInfo(Context context) {
    return context.hasKey(AUTH_INFO_CLASS);
  }

  private static Mono<UserAuthInfo> getUserAuthInfo(Context context) {
    return context.<Mono<UserAuthInfo>>get(AUTH_INFO_CLASS);
  }


  public static Function<Context, Context> clear() {
    return (context) -> context.delete(AUTH_INFO_CLASS);
  }


  public static Context withUserAuthInfo(Mono<UserAuthInfo> userAuthInfo) {
    return Context.of(AUTH_INFO_CLASS, userAuthInfo);
  }


}
