package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zeal.zealsay.common.constant.enums.DictType;
import com.zeal.zealsay.converter.DictConvertMapper;
import com.zeal.zealsay.dto.request.DictAddRequest;
import com.zeal.zealsay.dto.request.DictRequest;
import com.zeal.zealsay.entity.Dict;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.mapper.DictMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

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

    @Autowired
    DictConvertMapper dictConvertMapper;

    /**
     * 查询省的接口.
     *
     * @author zhanglei
     * @date 2019-04-08  18:11
     */
    @Async
    public Future<List<Dict>> getProvinceList() {
        List<Dict> list = list(new QueryWrapper<Dict>()
                .eq("type", DictType.REGION)
                .likeLeft("code", "0000"));
        //排序
        list.sort(Comparator.comparing(Dict::getSort));
        return new AsyncResult<>(list);
    }

    /**
     * 查询市和区的接口.
     *
     * @author zhanglei
     * @date 2019-04-08  18:13
     */
    @Async
    public Future<List<Dict>> getRegionList(String code) {
        List<Dict> list = list(new QueryWrapper<Dict>()
                .eq("type", DictType.REGION)
                .eq("parent_code", code));
        //排序
        list.sort(Comparator.comparing(Dict::getSort));
        return new AsyncResult<>(list);
    }


    /**
     * 构造查询参数.
     *
     * @author zhanglei
     * @date 2020/6/30  5:11 下午
     */
    public QueryWrapper<Dict> buildParams(DictRequest dictRequest) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        if (Objects.nonNull(dictRequest.getCode())) {
            dictQueryWrapper.like("code", dictRequest.getCode());
        }
        if (Objects.nonNull(dictRequest.getParentCode())) {
            dictQueryWrapper.eq("parent_code", dictRequest.getParentCode());
        }
        if (StringUtils.isNotBlank(dictRequest.getType())) {
            dictQueryWrapper.eq("type", dictRequest.getType());
        }
        if (StringUtils.isNotBlank(dictRequest.getName())) {
            dictQueryWrapper.like("name", dictRequest.getName());
        }
        if (StringUtils.isNotBlank(dictRequest.getDescription())) {
            dictQueryWrapper.like("description", dictRequest.getDescription());
        }
        dictQueryWrapper.orderByDesc("sort");
        return dictQueryWrapper;
    }

    /**
     * 查询所有的type.
     *
     * @author zhanglei
     * @date 2020/6/30  5:20 下午
     */
    public List<Dict> getTypeList(DictRequest dictRequest) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(dictRequest.getDescription())) {
            dictQueryWrapper.like("description", dictRequest.getDescription());
        }
        dictQueryWrapper.groupBy("type");
        return list(dictQueryWrapper);
    }

    /**
     * 获取系统配置.
     *
     * @author zhanglei
     * @date 2020/6/30  5:20 下午
     * @return
     */
    public Future<List<Dict>> getConfig() {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("type", "SYS_CONFIG");
        List<Dict> list = list(dictQueryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        list.sort(Comparator.comparing(Dict::getSort));
        return new AsyncResult<>(list);
    }


    /**
     * 添加字典.
     *
     * @author zhanglei
     * @date 2020/6/30  6:33 下午
     */
    public Boolean save(DictAddRequest dictRequest) {
        int count = count(new QueryWrapper<Dict>().eq("code", dictRequest.getCode()));
        if (count > 0) {
            throw new ServiceException("code不能重复");
        }
        return save(dictConvertMapper.toDict(dictRequest));
    }

    /**
     * 添加字典.
     *
     * @author zhanglei
     * @date 2020/6/30  6:33 下午
     */
    public Boolean updateDictById(Dict dict) {
        Dict d = getById(dict.getId());
        if (Objects.isNull(d)) {
            throw new ServiceException("找不到该字典");
        }
        if (Objects.isNull(dict.getType()) || (d.getType().contains("SYS_CONFIG")
                && !dict.getType().equals(d.getType()))) {
            throw new ServiceException("系统参数不允许随意更改type");
        }
        return updateById(dict);
    }

    /**
     * 根据id删除.
     *
     * @author zhanglei
     * @date 2020/6/30  6:33 下午
     */
    public Boolean delete(Long id) {
        Dict dict = getById(id);
        if (Objects.isNull(dict)) {
            throw new ServiceException("找不到该字典");
        }
        if (Objects.isNull(dict.getType()) || dict.getType().contains("SYS_CONFIG")) {
            throw new ServiceException("系统参数不能删除");
        }
        return removeById(id);
    }

    /**
     * 根据id删除.
     *
     * @author zhanglei
     * @date 2020/6/30  6:33 下午
     */
    public Boolean deleteBatch(List<Long> ids) {
        List<Dict> dicts = listByIds(ids);
        if (!CollectionUtils.isEmpty(dicts)) {
            List<Long> temps = dicts.stream()
                    .filter(d -> !d.getType().contains("SYS_CONFIG"))
                    .map(s -> s.getId())
                    .collect(Collectors.toList());
            removeByIds(temps);
            return true;
        }
        return false;
    }
}
