package vin.pth.security.core.config;

import java.io.Serializable;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Cocoon
 * @date 2022/11/15
 */
@ConfigurationProperties(prefix = "pth.security")
@Data
public class SecurityProperties implements Serializable {

}
