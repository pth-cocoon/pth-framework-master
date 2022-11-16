package vin.pth.security.authorization.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Cocoon
 * @date 2022/11/15
 */
@ConfigurationProperties(prefix = "pth.security.authorization")
@Data
public class AuthorizationProperties {

}
