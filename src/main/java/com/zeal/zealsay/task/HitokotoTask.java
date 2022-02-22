//package com.zeal.zealsay.task;
//
//import com.zeal.zealsay.service.PhraseService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
///**
// * 定时任务查询一言.
// *
// * @author  zhanglei
// * @date 2020/2/3  12:28 下午
// */
//@Slf4j
//@Component
//public class HitokotoTask {
//
//  @Autowired
//  PhraseService phraseService;
//
//  /**
//   * 每隔半小时执行一次
//   */
//  @Scheduled(cron="0 0/30 * * * ?")
//  public void taskStorePhrase() {
//    log.info("开始执行定时task获取一言");
//    phraseService.save();
//  }
//}
