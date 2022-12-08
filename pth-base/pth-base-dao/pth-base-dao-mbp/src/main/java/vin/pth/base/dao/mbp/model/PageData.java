package vin.pth.base.dao.mbp.model;

import com.github.pagehelper.Page;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import lombok.Data;

/**
 * @author Cocoon
 * @date 2022/11/22
 */
@Data
public class PageData<T> implements Serializable {

  private long pageSize;
  private long pageNumber;
  private long total;
  private long pageTotal;
  private Collection<T> list;

  public PageData(Page<T> page) {
    this.pageSize = page.getPageSize();
    this.pageNumber = page.getPageNum();
    this.total = page.getTotal();
    this.pageTotal = page.getPages();
    this.list = page.getResult();
  }

  public PageData(Page<?> page, List<T> list) {
    this.pageSize = page.getPageSize();
    this.pageNumber = page.getPageNum();
    this.total = page.getTotal();
    this.pageTotal = page.getPages();
    this.list = list;
  }
}
