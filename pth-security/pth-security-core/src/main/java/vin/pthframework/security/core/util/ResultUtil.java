package vin.pthframework.security.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import vin.pthframework.security.core.enums.SecurityErrorCode;
import vin.pthframework.security.core.exception.BaseSecurityException;

/**
 * @author Cocoon
 */
@Slf4j
public class ResultUtil {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String CODE_KEY = "code";
  private static final String MSG_KEY = "message";
  private static final String DATA_KEY = "data";
  private static final int SUCCESS_CODE = 0;

  private ResultUtil() {
  }

  public static Map<String, Object> error(int code, String message) {
    var result = new HashMap<String, Object>(3);
    result.put(CODE_KEY, code);
    result.put(MSG_KEY, message);
    return result;
  }

  public static Map<String, Object> success(Object data) {
    var result = new HashMap<String, Object>(3);
    result.put(CODE_KEY, SUCCESS_CODE);
    result.put(MSG_KEY, "Success");
    result.put(DATA_KEY, data);
    return result;
  }

  public static Map<String, Object> error(SecurityErrorCode securityErrorCode) {
    return error(securityErrorCode.getCode(), securityErrorCode.getMessage());
  }

  public static String toResultStr(Object o) {
    try {
      return OBJECT_MAPPER.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      log.error("e", e);
      return "";
    }
  }

  public static Map<String, Object> error(BaseSecurityException e) {
    return error(e.getCode(), e.getMessage());
  }
}
