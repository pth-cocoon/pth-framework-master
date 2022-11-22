package vin.pth.session.reactive.sample.pojo;

import lombok.Data;
import vin.pth.security.authentication.provider.Certificate;

/**
 * @author Cocoon
 * @date 2022/11/11
 */
@Data
public class UsernamePasswordDTO implements Certificate {

  private String username;
  private String password;

}
