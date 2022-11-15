package vin.pth.session.core.config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import vin.pth.session.core.context.PthSession;
import vin.pth.session.core.repository.SessionRepository;

/**
 * @author Cocoon
 * @date 2022/11/14
 */
@Slf4j
@Import({ReactiveSessionConfig.class, ServletSessionConfig.class})
@Configuration
@EnableConfigurationProperties(PthSessionProperties.class)
public class SessionCoreConfig {

  @ConditionalOnProperty(value = "pth.session.store-type", havingValue = "MEMORY", matchIfMissing = true)
  @ConditionalOnMissingBean(SessionRepository.class)
  @Bean
  public SessionRepository sessionRepository() {
    return new SessionRepository() {
      private final static Map<String, PthSession> SESSION_DATA = new HashMap<>(8);

      @Override
      public PthSession getBySessionId(@NonNull String sessionId) {
        return SESSION_DATA.get(sessionId);
      }

      @Override
      public PthSession getOrCreateSession(@Nullable String sessionId) {
        PthSession session = null;
        if (StringUtils.hasText(sessionId)) {
          session = getBySessionId(sessionId);
        }
        if (session != null) {
          return session;
        }
        sessionId = UUID.randomUUID().toString().replaceAll("-", "");
        SESSION_DATA.put(sessionId, new PthSession(sessionId));
        return getBySessionId(sessionId);
      }

      @Override
      public void commitSession(@NonNull PthSession pthSession) {
        String sessionId = pthSession.getSessionId();
        SESSION_DATA.put(sessionId, pthSession);
        log.info("Commit session,sessionId:{}", pthSession.getSessionId());
      }

    };
  }

}
