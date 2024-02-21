

package vin.pthframework.security.reactive.util;

import java.util.function.Function;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import vin.pthframework.security.core.context.SecurityContext;

/**
 * Allows getting and setting the Spring {@link SecurityContext} into a {@link Context}.
 *
 * @author Rob Winch
 * @since 5.0
 */
public final class ReactiveSecurityContextHolder {

  private static final Class<?> SECURITY_CONTEXT_KEY = SecurityContext.class;

  private ReactiveSecurityContextHolder() {
  }

  public static Mono<SecurityContext> getContext() {
    return Mono.subscriberContext()
        .filter(ReactiveSecurityContextHolder::hasSecurityContext)
        .flatMap(ReactiveSecurityContextHolder::getSecurityContext);
  }

  private static boolean hasSecurityContext(Context context) {
    return context.hasKey(SECURITY_CONTEXT_KEY);
  }

  private static Mono<SecurityContext> getSecurityContext(Context context) {
    return context.<Mono<SecurityContext>>get(SECURITY_CONTEXT_KEY);
  }


  public static Function<Context, Context> clearContext() {
    return (context) -> context.delete(SECURITY_CONTEXT_KEY);
  }


  public static Context withSecurityContext(Mono<SecurityContext> securityContext) {
    return Context.of(SECURITY_CONTEXT_KEY, securityContext);
  }


}
