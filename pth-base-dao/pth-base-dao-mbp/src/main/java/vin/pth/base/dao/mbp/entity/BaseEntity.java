package vin.pth.base.dao.mbp.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

  @TableId
  private Integer id;

  @TableField(insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
  private LocalDateTime createTime;

  @TableField(insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
  private LocalDateTime updateTime;

  private String createUserId;

  private String updateUserId;

}
