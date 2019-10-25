package com.zeal.zealsay.mapper;

import com.zeal.zealsay.entity.Phrase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhanglei
 * @since 2019-06-30
 */
public interface PhraseMapper extends BaseMapper<Phrase> {

  /**
   * 随机获取一条.
   *
   * @author  zhanglei
   * @date 2019-10-25  18:18
   */
  Phrase randomPhrase();
}
