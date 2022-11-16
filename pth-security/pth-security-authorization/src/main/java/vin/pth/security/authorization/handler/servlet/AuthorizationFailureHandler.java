package vin.pth.security.authorization.handler.servlet;

import javax.servlet.http.HttpServletResponse;
import vin.pth.security.core.exception.AuthorizationException;

/**
 * @author Cocoon
 * @date 2022/11/15
 */
public interface AuthorizationFailureHandler {

  default void failureHandler(HttpServletResponse response, AuthorizationException e) {
  }

}
