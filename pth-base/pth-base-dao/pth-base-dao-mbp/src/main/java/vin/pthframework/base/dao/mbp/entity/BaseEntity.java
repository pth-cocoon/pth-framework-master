package vin.pthframework.base.dao.mbp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Cocoon
 * @date 2022/11/22
 */
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

  private Integer id;

  @TableField(insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
  private LocalDateTime updateTime;

  @TableField(insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
  private LocalDateTime createTime;

  @TableField(fill = FieldFill.INSERT)
  private Integer createUserId;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Integer updateUserId;

}
