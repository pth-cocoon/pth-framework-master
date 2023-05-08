package vin.pthframework.base.common.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author Cocoon
 * @date 2022/11/27
 */
@Data
public class Resp<T> implements Serializable {

  public static final int SUCCESS_CODE = 0;
  public static final int DEFAULT_ERROR_CODE = 1;
  public static final String SUCCESS_MSG = "SUCCESS";
  private final int code;
  private final String message;
  private final T data;

  private Resp() {
    this.code = 0;
    this.message = SUCCESS_MSG;
    this.data = null;
  }

  private Resp(int code, String message) {
    this.code = code;
    this.message = message;
    this.data = null;

  }

  private Resp(int code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public static <T> Resp<T> success() {
    return new Resp<>(SUCCESS_CODE, SUCCESS_MSG, null);
  }

  public static <T> Resp<T> success(T data) {
    return new Resp<>(SUCCESS_CODE, SUCCESS_MSG, data);
  }

  public static <T> Resp<T> error(int code, String message) {
    return new Resp<>(code, message);
  }

  public static <T> Resp<T> error(String message) {
    return error(DEFAULT_ERROR_CODE, message);
  }

  public boolean isSuccess() {
    return this.code == SUCCESS_CODE;
  }


}
