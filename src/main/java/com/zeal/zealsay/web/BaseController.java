package com.zeal.zealsay.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 基本控制类.
 *
 * @author  zhanglei
 * @date 2018/9/7  下午6:42
 */
@Slf4j
public class BaseController<S extends IService<T>, T> {

  @Autowired
  protected S service;

  /**
   * 根据id来查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/{id}")
  public ResponseEntity<Result<T>> getById(@PathVariable String id) {
    log.info("Start get for detail by id '{}'", id);
    return ResponseEntity.ok(Result.of(service.getById(id)));
  }

  /**
   * 查询列表.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("")
  public ResponseEntity<Result<List<T>>> getList(T t) {
    log.info("start get for list by query entity '{}'", t);
    return ResponseEntity.ok(Result.of(service.list(new QueryWrapper<>(t))));
  }

  /**
   * 分页查询.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @GetMapping("/page")
  public ResponseEntity<Result<PageInfo<T>>> getByPaginate(Long pageNumber, Long pageSize,T t) {
    log.info("start get for page list by query entity '{}'", t);
    if (null!=pageNumber&&null!=pageSize){
      return ResponseEntity.ok(Result.of(new PageInfo((Page) service
          .page(new Page<>(pageNumber,pageSize),new QueryWrapper<>(t)))));
    }else {
      return ResponseEntity.ok(Result.of(new PageInfo((Page) service
          .page(new Page<>(),new QueryWrapper<>(t)))));
    }
  }

  /**
   * 新增一条记录.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @PostMapping("")
  public ResponseEntity<Result<Boolean>> insert(@RequestBody T entity) {
    log.info("start add a item with entity '{}'", entity);
    return ResponseEntity.ok(Result.of(service.save(entity)));
  }
//
//  /**
//   * 批量新增记录.
//   *
//   * @author zhanglei
//   * @date 2018/9/7  下午6:01
//   */
//  @PostMapping("/batch")
//  public ResponseEntity<Result<Boolean>> insertBatch(@RequestBody List<T> entityList) {
//    log.info("start add  item batch with entityList '{}'", entityList);
//    return ResponseEntity.ok(Result.of(service.saveBatch(entityList)));
//  }
//
//  /**
//   * 根据id修改一条记录.
//   *
//   * @author zhanglei
//   * @date 2018/9/7  下午6:01
//   */
//  @PutMapping("")
//  public ResponseEntity<Result<Boolean>> update(@RequestBody T entity) {
//    log.info("start update item with entity '{}'", entity);
//    return ResponseEntity.ok(Result.of(service.updateById(entity)));
//  }
//
//  /**
//   * 根据id 批量修改记录.
//   *
//   * @author zhanglei
//   * @date 2018/9/7  下午6:01
//   */
//  @PutMapping("/batch")
//  public ResponseEntity<Result<Boolean>> updateBatch(@RequestBody List<T> entityList) {
//    log.info("start update  item batch with entityList '{}'", entityList);
//    return ResponseEntity.ok(Result.of(service.updateBatchById(entityList)));
//  }

  /**
   * 根据id删除一条记录.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:01
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Result<Boolean>> delete(@PathVariable String id) {
    log.info("start delete  item batch with id '{}'", id);
    return ResponseEntity.ok(Result.of(service.removeById(id)));
  }

}
