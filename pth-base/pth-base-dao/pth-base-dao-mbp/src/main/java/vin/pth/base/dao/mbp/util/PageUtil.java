package vin.pth.base.dao.mbp.util;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import vin.pth.base.dao.mbp.model.PageData;
import vin.pth.base.dao.mbp.model.PageParam;

/**
 * @author Cocoon
 * @date 2022/11/22
 */
@Slf4j
public class PageUtil {

  public static <T> PageData<T> page(PageParam param, ISelect select) {
    try (Page<T> page = PageHelper.startPage(param.getPageNumber(), param.getPageSize())
        .doSelectPage(select)) {
      return new PageData<>(page);
    } catch (Exception e) {
      log.error("分页出错：e", e);
      throw e;
    }
  }

}
