package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
 * 抽象服务类.
 *
 * @author zhanglei
 * @date 2019-11-05  14:56
 */
public abstract class AbstractService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

}
