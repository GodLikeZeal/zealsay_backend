package com.zeal.zealsay.service;

import com.zeal.zealsay.entity.Phrase;
import com.zeal.zealsay.feign.HitokotoClient;
import com.zeal.zealsay.feign.response.HitokotoResponse;
import com.zeal.zealsay.mapper.PhraseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2019-06-30
 */
@Slf4j
@Service
public class PhraseService extends AbstractService<PhraseMapper, Phrase> {

  @Autowired
  HitokotoClient hitokotoClient;

  /**
   * 获取一言接口.
   *
   * @author zeal
   * @date 2019/6/30 13:17
   */
  @Async
  public Future<HitokotoResponse> get() {
    HitokotoResponse hitokotoResponse;

    //随机取出一条
    Phrase phrase = baseMapper.randomPhrase();
    hitokotoResponse = HitokotoResponse.builder()
        .id(phrase.getId())
        .type(phrase.getType())
        .hitokoto(phrase.getHitokoto())
        .from(phrase.getSource())
        .creator(phrase.getCreator())
        .build();

    return new AsyncResult<>(hitokotoResponse);
  }

  /**
   * 保存.
   *
   * @author zhanglei
   * @date 2020/2/3  12:38 下午
   */
  public void save() {
      HitokotoResponse hitokotoResponse;
      try {
          hitokotoResponse = hitokotoClient.get();
          //本地备份
          savePhraseLocal(hitokotoResponse);
      } catch (Exception e) {
          //出错
          log.error("获取外部hitokoto接口失败，出错信息为{}", e.getMessage());
          e.printStackTrace();
      }
  }

  /**
   * 异步存储.
   *
   * @author zeal
   * @date 2019/6/30 13:17
   */
  @Async
  void savePhraseLocal(HitokotoResponse hitokotoResponse) {
    if (Objects.isNull(hitokotoResponse)) {
      return;
    }
    Phrase phrase = getById(hitokotoResponse.getId());
    if (Objects.nonNull(phrase)) {
      return;
    }
    save(Phrase.builder()
        .id(hitokotoResponse.getId())
        .hitokoto(hitokotoResponse.getHitokoto())
        .source(hitokotoResponse.getFrom())
        .creator(hitokotoResponse.getCreator())
        .type(hitokotoResponse.getType())
        .createdAt(Instant
            .ofEpochMilli(hitokotoResponse.getCreated_at())
            .atZone(ZoneId.systemDefault()).toLocalDate())
        .build());
  }
}
