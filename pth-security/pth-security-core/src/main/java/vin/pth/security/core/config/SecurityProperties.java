package vin.pth.security.core.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import vin.pth.security.core.model.Authority;

/**
 * @author Cocoon
 * @date 2022/11/15
 */
@ConfigurationProperties(prefix = "pth.security")
@Data
public class SecurityProperties implements Serializable {

  /**
   * 白名单.
   */
  private Collection<Authority> writeList = new ArrayList<>();

}
