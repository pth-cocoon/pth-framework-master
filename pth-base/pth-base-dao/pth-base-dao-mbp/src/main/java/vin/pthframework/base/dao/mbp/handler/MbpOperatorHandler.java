package vin.pthframework.base.dao.mbp.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.reflection.MetaObject;

/**
 * @author Cocoon
 */
@RequiredArgsConstructor
public class MbpOperatorHandler implements MetaObjectHandler {

  private final CurrentUserService currentUserService;

  @Override
  public void insertFill(MetaObject metaObject) {
    this.strictFillStrategy(metaObject, "createUserId", this::getCurrentUserId);
    this.strictFillStrategy(metaObject, "updateUserId", this::getCurrentUserId);

  }

  @Override
  public void updateFill(MetaObject metaObject) {
    this.strictFillStrategy(metaObject, "updateUserId", this::getCurrentUserId);
  }

  private Integer getCurrentUserId() {
    Integer userId = currentUserService.currentUserId();
    if (userId == null) {
      userId = 0;
    }
    return userId;
  }
}
