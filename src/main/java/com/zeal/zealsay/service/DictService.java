package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeal.zealsay.common.constant.enums.DictType;
import com.zeal.zealsay.entity.Dict;
import com.zeal.zealsay.mapper.DictMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Future;

/**
 * <p>
 * 全局字典表 服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2019-03-27
 */
@Service
public class DictService extends AbstractService<DictMapper, Dict> {

  /**
   * 查询省的接口.
   *
   * @author  zhanglei
   * @date 2019-04-08  18:11
   */
  @Async
  public Future<List<Dict>> getProvinceList() {
    List<Dict> list = list(new QueryWrapper<Dict>()
        .eq("type", DictType.REGION)
        .likeLeft("code","0000"));
    //排序
    list.sort(Comparator.comparing(Dict::getSort));
    return new AsyncResult<>(list);
  }

  /**
   * 查询市和区的接口.
   *
   * @author  zhanglei
   * @date 2019-04-08  18:13
   */
  @Async
  public Future<List<Dict>> getRegionList(String code) {
    List<Dict> list = list(new QueryWrapper<Dict>()
        .eq("type", DictType.REGION)
        .eq("parent_code",code));
    //排序
    list.sort(Comparator.comparing(Dict::getSort));
    return new AsyncResult<>(list);
  }
}
