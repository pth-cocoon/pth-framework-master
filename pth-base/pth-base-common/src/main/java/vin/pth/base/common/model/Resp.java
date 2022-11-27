package vin.pth.base.common.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author Cocoon
 * @date 2022/11/27
 */
@Data
public class Resp<T> implements Serializable {

  private final int code;
  private final String message;
  private final T data;
  public final static int SUCCESS_CODE = 0;
  public final static int DEFAULT_ERROR_CODE = 1;

  public boolean isSuccess() {
    return this.code == SUCCESS_CODE;
  }

  private Resp() {
    this.code = 0;
    this.message = "Success";
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
    return new Resp<>(SUCCESS_CODE, "Success", null);
  }

  public static <T> Resp<T> success(T data) {
    return new Resp<>(SUCCESS_CODE, "Success", data);
  }

  public static <T> Resp<T> error(int code, String message) {
    return new Resp<>(code, message);
  }

  public static <T> Resp<T> error(String message) {
    return error(DEFAULT_ERROR_CODE, message);
  }


}
