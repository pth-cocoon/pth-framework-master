
package vin.pth.session.core.context;

import java.util.function.Function;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;


/**
 * @author Cocoon
 */
public final class ReactiveSessionHolder {

  private static final Class<?> SECURITY_CONTEXT_KEY = PthSession.class;

  private ReactiveSessionHolder() {
  }

  public static Mono<PthSession> getSession() {
    // @formatter:off
    return Mono.subscriberContext()
        .filter(ReactiveSessionHolder::hasSession)
        .flatMap(ReactiveSessionHolder::getSession);
    // @formatter:on
  }

  private static boolean hasSession(Context context) {
    return context.hasKey(SECURITY_CONTEXT_KEY);
  }

  private static Mono<PthSession> getSession(Context context) {
    return context.<Mono<PthSession>>get(SECURITY_CONTEXT_KEY);
  }

  public static Function<Context, Context> clearSession() {
    return (context) -> context.delete(SECURITY_CONTEXT_KEY);
  }


  public static Context withSessionContext(Mono<PthSession> securityContext) {
    return Context.of(SECURITY_CONTEXT_KEY, securityContext);
  }

  public static Context withSession(PthSession session) {
    return withSessionContext(Mono.just(session));
  }

}
