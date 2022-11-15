package vin.pth.session.core.repository;

import vin.pth.session.core.context.PthSession;


/**
 * @author Cocoon
 */
public interface SessionRepository {

  /**
   * 获取session.
   *
   * @param sessionId Session.
   * @return CaSession.
   */
  PthSession getBySessionId(String sessionId);

  /**
   * @param sessionId SessionId
   * @return PthSession
   */
  PthSession getOrCreateSession(String sessionId);

  /**
   * @param pthSession PthSession.
   */
  void commitSession(PthSession pthSession);
}