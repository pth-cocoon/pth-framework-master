package vin.pthframework.security.servlet.handler;

import javax.servlet.http.HttpServletResponse;
import vin.pthframework.security.core.exception.BaseSecurityException;

public interface AuthorizationFailureHandler {

  void failureHandler(HttpServletResponse response, BaseSecurityException e);


}
