package vin.pthframework.security.servlet.util;


import vin.pthframework.security.core.context.SecurityContext;

/**
 * @author Cocoon
 */
public class SecurityContextHolder {

  private static final ThreadLocal<SecurityContext> CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

  public static SecurityContext getContext() {
    return CONTEXT_THREAD_LOCAL.get();
  }

  public static void setContext(SecurityContext securityContext) {
    CONTEXT_THREAD_LOCAL.set(securityContext);
  }

  public static void clear() {
    CONTEXT_THREAD_LOCAL.remove();
  }

}
