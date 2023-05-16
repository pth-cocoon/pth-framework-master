package vin.pthframework.security.servlet.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Cocoon
 */
@ComponentScan(basePackages = "vin.pthframework.security.servlet.*")
@Import({SecurityBeanConfig.class})
@Configuration
public class SecurityServletConfig {


}
