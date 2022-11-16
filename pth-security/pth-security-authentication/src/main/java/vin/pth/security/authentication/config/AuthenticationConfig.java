package vin.pth.security.authentication.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import vin.pth.security.authentication.provider.ProviderManager;

/**
 * @author Cocoon
 * @date 2022/11/15
 */
@Slf4j
@Import({ProviderManager.class})
@EnableConfigurationProperties(AuthenticationProperties.class)
@Configuration
@ComponentScan("vin.pth.security.authentication")
public class AuthenticationConfig {

}
