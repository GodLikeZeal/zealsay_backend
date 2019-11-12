package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 抽象服务类.
 *
 * @author zhanglei
 * @date 2019-11-05  14:56
 */
public abstract class AbstractService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

  @Override
  public boolean save(T entity) {
    return super.save(entity);
  }

  @Override
  public boolean saveBatch(Collection<T> entityList, int batchSize) {
    return super.saveBatch(entityList, batchSize);
  }

  @Override
  public boolean saveOrUpdate(T entity) {
    return super.saveOrUpdate(entity);
  }

  @Override
  public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
    return super.saveOrUpdateBatch(entityList, batchSize);
  }

  @Override
  public boolean removeById(Serializable id) {
    return super.removeById(id);
  }

  @Override
  public boolean removeByMap(Map<String, Object> columnMap) {
    return super.removeByMap(columnMap);
  }

  @Override
  public boolean remove(Wrapper<T> wrapper) {
    return super.remove(wrapper);
  }

  @Override
  public boolean removeByIds(Collection<? extends Serializable> idList) {
    return super.removeByIds(idList);
  }

  @Override
  public boolean updateById(T entity) {
    return super.updateById(entity);
  }

  @Override
  public boolean update(T entity, Wrapper<T> updateWrapper) {
    return super.update(entity, updateWrapper);
  }

  @Override
  public boolean updateBatchById(Collection<T> entityList, int batchSize) {
    return super.updateBatchById(entityList, batchSize);
  }

  @Override
  public T getById(Serializable id) {
    return super.getById(id);
  }

  @Override
  public Collection<T> listByIds(Collection<? extends Serializable> idList) {
    return super.listByIds(idList);
  }

  @Override
  public Collection<T> listByMap(Map<String, Object> columnMap) {
    return super.listByMap(columnMap);
  }

  @Override
  public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
    return super.getOne(queryWrapper, throwEx);
  }

  @Override
  public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
    return super.getMap(queryWrapper);
  }

  @Override
  public int count(Wrapper<T> queryWrapper) {
    return super.count(queryWrapper);
  }

  @Override
  public List<T> list(Wrapper<T> queryWrapper) {
    return super.list(queryWrapper);
  }

  @Override
  public IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper) {
    return super.page(page, queryWrapper);
  }

  @Override
  public List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
    return super.listMaps(queryWrapper);
  }

  @Override
  public <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
    return super.listObjs(queryWrapper, mapper);
  }

  @Override
  public IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper) {
    return super.pageMaps(page, queryWrapper);
  }

  @Override
  public <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
    return super.getObj(queryWrapper, mapper);
  }

  @Override
  public boolean saveBatch(Collection<T> entityList) {
    return false;
  }

  @Override
  public boolean saveOrUpdateBatch(Collection<T> entityList) {
    return false;
  }

  @Override
  public boolean update(Wrapper<T> updateWrapper) {
    return false;
  }

  @Override
  public boolean updateBatchById(Collection<T> entityList) {
    return false;
  }

  @Override
  public T getOne(Wrapper<T> queryWrapper) {
    return null;
  }

  @Override
  public int count() {
    return 0;
  }

  @Override
  public List<T> list() {
    return null;
  }

  @Override
  public IPage<T> page(IPage<T> page) {
    return null;
  }

  @Override
  public List<Map<String, Object>> listMaps() {
    return null;
  }

  @Override
  public List<Object> listObjs() {
    return null;
  }

  @Override
  public <V> List<V> listObjs(Function<? super Object, V> mapper) {
    return null;
  }

  @Override
  public List<Object> listObjs(Wrapper<T> queryWrapper) {
    return null;
  }

  @Override
  public IPage<Map<String, Object>> pageMaps(IPage<T> page) {
    return null;
  }

  @Override
  public boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper) {
    return false;
  }

}
