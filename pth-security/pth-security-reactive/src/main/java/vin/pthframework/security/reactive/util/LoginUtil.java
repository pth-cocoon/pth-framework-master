package vin.pthframework.security.reactive.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.server.WebSession;
import vin.pthframework.security.core.consts.SecurityConst;
import vin.pthframework.session.pojo.UserAuthInfo;

/**
 * @author Cocoon
 */
@Slf4j
public class LoginUtil {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private LoginUtil() {

  }


  public static void login(UserAuthInfo userAuthInfo, WebSession session) {
    Assert.notNull(userAuthInfo, "用户信息不允许为空");
    try {
      session.getAttributes()
          .put(SecurityConst.USER_INFO_KEY, OBJECT_MAPPER.writeValueAsString(userAuthInfo));
    } catch (JsonProcessingException e) {
      log.error("e", e);
    }
  }

  public static UserAuthInfo getAuthInfo(WebSession session) {
    Object json = session.getAttributes().get(SecurityConst.USER_INFO_KEY);
    try {
      return OBJECT_MAPPER.readValue(json + "", UserAuthInfo.class);
    } catch (JsonProcessingException e) {
      log.error("用户信息序列化失败：", e);
      return null;
    }
  }
}
