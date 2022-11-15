package vin.pth.session.data.repository;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import vin.pth.session.core.config.PthSessionProperties;
import vin.pth.session.core.context.PthSession;
import vin.pth.session.core.repository.SessionRepository;
import vin.pth.session.data.config.PthRedisSessionProperties;

/**
 * @author Cocoon
 * @date 2022/11/14
 */
@ConditionalOnProperty(value = "pth.session.store-type", havingValue = "REDIS")
@Component
@RequiredArgsConstructor
public class RedisSessionRepository implements SessionRepository {

  private final PthSessionProperties sessionProperties;
  private final PthRedisSessionProperties redisSessionProperties;
  private final RedisTemplate<Object, Object> redisTemplate;


  /**
   * 获取session.
   *
   * @param sessionId Session.
   * @return CaSession.
   */
  @Override
  public PthSession getBySessionId(String sessionId) {
    return (PthSession) redisTemplate.opsForValue().get(buildKey(sessionId));
  }

  /**
   * @param sessionId String
   * @return PthSession
   */
  @Override
  public PthSession getOrCreateSession(String sessionId) {
    if (StringUtils.hasText(sessionId)) {
      PthSession session = getBySessionId(sessionId);
      if (session != null) {
        return session;
      }
    }
    sessionId = UUID.randomUUID().toString().replaceAll("-", "");
    return new PthSession(sessionId);
  }

  @Override
  public void commitSession(PthSession pthSession) {
    Assert.notNull(pthSession, "PthSession为空!");
    Assert.isTrue(StringUtils.hasText(pthSession.getSessionId()), "SessionId为空！");
    String sessionId = pthSession.getSessionId();
    redisTemplate.opsForValue()
        .set(buildKey(sessionId), pthSession, sessionProperties.getTimeout());
  }

  private String buildKey(String sessionId) {
    String sessionKey = sessionProperties.getSessionIdKey();
    String namespace = redisSessionProperties.getNamespace();
    return namespace + ":" + sessionKey + ":" + sessionId;

  }


}
