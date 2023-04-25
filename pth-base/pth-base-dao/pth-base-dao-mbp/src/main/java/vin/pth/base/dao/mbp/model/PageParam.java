package vin.pth.base.dao.mbp.model;

import lombok.Setter;

import java.io.Serializable;

/**
 * @author Cocoon
 * @date 2022/12/14
 */
@Setter
public class PageParam implements Serializable {

  private Integer pageSize;
  private Integer pageNumber;

  public Integer getPageNumber() {
    if (this.pageNumber == null || this.pageNumber < 1) {
      return 1;
    }
    return pageNumber;
  }

  public Integer getPageSize() {
    if (this.pageSize == null || pageSize < 1) {
      return 10;
    }
    return pageSize;
  }
}
