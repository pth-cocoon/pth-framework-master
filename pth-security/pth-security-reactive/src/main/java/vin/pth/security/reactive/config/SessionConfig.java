package vin.pth.security.reactive.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;
import vin.pth.security.core.config.SecurityCoreProperties;

/**
 * @author Cocoon
 */
@Slf4j
@Configuration
public class SessionConfig {


    @Primary
    @Bean
    public WebSessionIdResolver mywebSessionIdResolver(SecurityCoreProperties securityCoreProperties) {
        switch (securityCoreProperties.getSessionPosition()) {
            case COOKIE -> {
                var resolver = new CookieWebSessionIdResolver();
                resolver.setCookieName(securityCoreProperties.getSessionKey());
                resolver.addCookieInitializer(builder -> builder.httpOnly(true));
                return resolver;
            }
            case HEADER -> {
                var headerWebSessionIdResolver = new HeaderWebSessionIdResolver();
                headerWebSessionIdResolver.setHeaderName(securityCoreProperties.getSessionKey());
                return headerWebSessionIdResolver;
            }
            default -> {
                return new CookieWebSessionIdResolver();
            }
        }
    }


}
