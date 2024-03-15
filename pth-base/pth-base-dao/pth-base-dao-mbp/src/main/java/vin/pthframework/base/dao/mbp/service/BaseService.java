package vin.pthframework.base.dao.mbp.service;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

  protected void createCheck() {

  }

  protected void updateCheck() {

  }

  protected void deleteCheck() {

  }

  @Getter
  @SuppressWarnings("unchecked")
  private final Class<T> modelClass =
      (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

  public T getById(Integer id) {
    Assert.notNull(id, "ID为空");
    return Db.getById(id, getModelClass());
  }

  /**
   * 创建数据.
   *
   * @param model 创建.
   * @return model.
   */
  @SuppressWarnings("unused")
  public T create(T model) {
    createCheck();
    Assert.notNull(model, "数据为空");
    Assert.isNull(model.getId(), "创建操作id必须为空");
    boolean result = Db.save(model);
    Assert.isTrue(result, "创建数据失败");
    return getById(model.getId());
  }


  /**
   * 更新数据.
   *
   * @param model 根据ID update
   * @return model.
   */
  public T update(T model) {
    updateCheck();
    Assert.notNull(model, "数据为空");
    Assert.notNull(model.getId(), "更新操作id不允许为空");
    Assert.isTrue(model.getId() > 0, "无法更新Id=0的数据");
    boolean result = Db.updateById(model);
    Assert.isTrue(result, "更新数据失败");
    return getById(model.getId());
  }

  /**
   * 根据ID删除
   *
   * @param id 主键
   */
  public void deleteById(Integer id) {
    deleteCheck();
    boolean result = Db.removeById(id, getModelClass());
    Assert.isTrue(result, "删除数据失败");
  }

  public void batchCreate(Collection<T> model) {
    model.forEach(this::create);
  }

  public void batchUpdate(Collection<T> models) {
    models.forEach(this::update);
  }

  public void batchDelete(Collection<Integer> id) {
    id.forEach(this::deleteById);
  }

  public List<T> findAll() {
    return Db.list(getModelClass());
  }

  public List<T> find(AbstractWrapper<T, ?, ?> queryWrapper) {
    return Db.list(queryWrapper);
  }

  public List<T> findByIds(Collection<Integer> ids) {
    LambdaQueryWrapper<T> baseWrapper = getBaseWrapper();
    baseWrapper.in(BaseEntity::getId, ids);
    return Db.list(baseWrapper);
  }

  public T getOne(AbstractWrapper<T, ?, ?> wrapper) {
    return Db.getOne(wrapper);
  }

  public LambdaQueryWrapper<T> getBaseWrapper() {
    return new LambdaQueryWrapper<>(getModelClass());
  }


}
