package vin.pth.session.core.context;

import org.springframework.lang.NonNull;

/**
 * @author Cocoon
 * @date 2022/11/14
 */
public final class ServletSessionHolder {

  private final static ThreadLocal<PthSession> SESSION_THREAD_LOCAL = new ThreadLocal<>();


  public static void setSession(@NonNull PthSession session) {
    SESSION_THREAD_LOCAL.set(session);
  }

  public static PthSession getSession() {
    return SESSION_THREAD_LOCAL.get();
  }

  public static void clearSession() {
    SESSION_THREAD_LOCAL.remove();
  }


}
