package vin.pthframework.security.servlet.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import vin.pthframework.session.consts.SecurityConst;
import vin.pthframework.session.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
@Slf4j
@SuppressWarnings("unused")
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
    login(userAuthInfo, getRequest().getSession());
  }

  public static UserAuthInfo getAuthInfo() {
    return getAuthInfo(getRequest().getSession());
  }

  private static HttpServletRequest getRequest() {
    var request = HttpUtil.getRequest();
    Assert.notNull(request, "request is null");
    return request;
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
    Assert.notNull(request, "request is null");
    request.getSession().removeAttribute(SecurityConst.USER_INFO_KEY);
  }
}
