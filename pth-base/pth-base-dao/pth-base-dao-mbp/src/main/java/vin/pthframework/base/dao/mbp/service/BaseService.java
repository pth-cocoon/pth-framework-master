package vin.pthframework.base.dao.mbp.service;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.util.Assert;
import vin.pthframework.base.dao.mbp.entity.BaseEntity;

/**
 * @author Cocoon
 */
@SuppressWarnings("unused")
public abstract class BaseService<T extends BaseEntity> {

  @Getter
  @SuppressWarnings("unchecked")
  private final Class<T> modelClass =
      (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

  public T getById(Integer id) {
    Assert.notNull(id, "ID为空");
    return Db.getById(id, getModelClass());
  }

  public T create(T model) {
    Assert.notNull(model, "数据为空");
    Assert.isNull(model.getId(), "创建操作id必须为空");
    boolean result = Db.save(model);
    Assert.isTrue(result, "创建数据失败");
    return getById(model.getId());
  }

  public void createBatch(Collection<T> model) {
    model.forEach(this::create);
  }

  public T update(T model) {
    Assert.notNull(model, "数据为空");
    Assert.notNull(model.getId(), "更新操作id不允许为空");
    boolean result = Db.updateById(model);
    Assert.isTrue(result, "更新数据失败");
    return getById(model.getId());
  }

  public void deleteById(Integer id) {
    boolean result = Db.removeById(id, getModelClass());
    Assert.isTrue(result, "删除数据失败");
  }

  public List<T> findAll() {
    return Db.list(getModelClass());
  }


}
