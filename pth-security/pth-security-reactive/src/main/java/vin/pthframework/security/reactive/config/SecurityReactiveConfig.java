package vin.pthframework.security.reactive.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Cocoon
 */
@Slf4j
@ComponentScan(basePackages = "vin.pthframework.security.reactive.*")
@Import({SecurityBeanConfig.class})
@Configuration
public class SecurityReactiveConfig {


}
