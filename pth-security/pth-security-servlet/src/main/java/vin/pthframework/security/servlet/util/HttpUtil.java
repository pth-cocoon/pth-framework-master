package vin.pthframework.security.servlet.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Cocoon
 */
@Slf4j
@SuppressWarnings("unused")
public class HttpUtil {

  public static HttpServletRequest getRequest() {
    var requestAttributes = RequestContextHolder.getRequestAttributes();
    if (requestAttributes == null) {
      log.error("requestAttributes is null");
      return null;
    }
    return ((ServletRequestAttributes) requestAttributes).getRequest();
  }

  public static HttpServletResponse getResponse() {
    var requestAttributes = RequestContextHolder.getRequestAttributes();
    if (requestAttributes == null) {
      log.error("requestAttributes is null");
      return null;
    }
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
  }
}
