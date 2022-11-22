package vin.pth.security.authorization.config;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import vin.pth.security.core.model.Authority;

/**
 * @author Cocoon
 * @date 2022/11/15
 */
@ConfigurationProperties("pth.security.authorization")
@Validated
@Data
public class AuthorizationProperties {

  private Collection<Authority> whiteList = new ArrayList<>();

}
