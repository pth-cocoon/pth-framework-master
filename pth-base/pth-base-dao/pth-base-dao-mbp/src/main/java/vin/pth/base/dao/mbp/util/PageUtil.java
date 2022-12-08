package vin.pth.base.dao.mbp.util;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import vin.pth.base.dao.mbp.model.PageData;

/**
 * @author Cocoon
 * @date 2022/11/22
 */
public class PageUtil {

  public static <T> PageData<T> page(ISelect select) {
    try (Page<T> page = PageHelper.startPage(1, 10).doSelectPage(select)) {
      return new PageData<>(page);
    }
  }

}
