package vin.pth.security.authorization.config;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import vin.pth.security.core.model.Authority;

/**
 * @author Cocoon
 * @date 2022/11/15
 */
@ConfigurationProperties(prefix = "pth.security.authorization")
@Data
public class AuthorizationProperties {

  private String test = "123";

  private Collection<Authority> whiteList = new ArrayList<>();

}
