package vin.pthframework.security.servlet.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import vin.pthframework.session.consts.SecurityConst;
import vin.pthframework.session.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
@Slf4j
public class LoginUtil {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  static {
    OBJECT_MAPPER.registerModule(new JavaTimeModule());
  }

  private LoginUtil() {
  }


  public static void login(UserAuthInfo userAuthInfo, HttpSession session) {
    try {
      session.setAttribute(SecurityConst.USER_INFO_KEY,
          OBJECT_MAPPER.writeValueAsString(userAuthInfo));
      UserAuthInfoHolder.setUserAuthInfo(userAuthInfo);
    } catch (JsonProcessingException e) {
      log.error("e", e);
    }
  }

  public static void login(UserAuthInfo userAuthInfo) {
    HttpServletRequest request = HttpUtil.getRequest();
    login(userAuthInfo, request.getSession());
  }

  public static UserAuthInfo getAuthInfo() {
    return getAuthInfo(HttpUtil.getRequest().getSession());
  }

  public static UserAuthInfo getAuthInfo(HttpSession session) {
    Object json = session.getAttribute(SecurityConst.USER_INFO_KEY);
    if (json == null) {
      return null;
    }
    try {
      return OBJECT_MAPPER.readValue(json + "", UserAuthInfo.class);
    } catch (JsonProcessingException e) {
      log.error("e", e);
      return null;
    }
  }

  public static void logout() {
    HttpServletRequest request = HttpUtil.getRequest();
    request.getSession().removeAttribute(SecurityConst.USER_INFO_KEY);
  }
}
