package com.zeal.zealsay.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * 线程池配置类.
 *
 * @author zhanglei
 * @date 2018/10/25  11:29 AM
 */
@Slf4j
@Configuration
public class AsyncTaskExecutePool implements AsyncConfigurer {


  /**
   * IO密集型任务  = 一般为2*CPU核心数（常出现于线程中：数据库数据交互、文件上传下载、网络数据传输等等）
   * CPU密集型任务 = 一般为CPU核心数+1（常出现于线程中：复杂算法）
   * 混合型任务  = 视机器配置和复杂度自测而定.
   */
  private static final int corePoolSize = Runtime.getRuntime().availableProcessors();

  /**
   * 最大线程数.
   */
  private static final int maxPoolSize = 2 * corePoolSize;
  /**
   * 队列容量.
   */
  private static final int queueCapacity = 1000;
  /**
   * 线程活跃时间.
   */
  private static final int keepAliveSeconds = 60;
  /**
   * 线程名称前缀.
   */
  private static final String threadNamePrefix = "Notification-";


  /**
   * corePoolSize用于指定核心线程数量
   * maximumPoolSize指定最大线程数
   * keepAliveTime和TimeUnit指定线程空闲后的最大存活时间
   * workQueue则是线程池的缓冲队列,还未执行的线程会在队列中等待
   * 监控队列长度，确保队列有界
   * 不当的线程池大小会使得处理速度变慢，稳定性下降，并且导致内存泄露。如果配置的线程过少，则队列会持续变大，消耗过多内存。
   * 而过多的线程又会 由于频繁的上下文切换导致整个系统的速度变缓——殊途而同归。队列的长度至关重要，它必须得是有界的，这样如果线程池不堪重负了它可以暂时拒绝掉新的请求。
   * ExecutorService 默认的实现是一个无界的 LinkedBlockingQueue.
   *
   * @author zhanglei
   * @date 2018/10/25  1:59 PM
   */
  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    // 设置核心线程数.
    executor.setCorePoolSize(corePoolSize);
    // 设置最大线程数.
    executor.setMaxPoolSize(maxPoolSize);
    // 设置队列容量.
    executor.setQueueCapacity(queueCapacity);
    // 设置线程活跃时间（秒）.
    executor.setKeepAliveSeconds(keepAliveSeconds);
    // 设置默认线程名称.
    executor.setThreadNamePrefix(threadNamePrefix);
    // 设置拒绝策略.
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    // 等待所有任务结束后再关闭线程池.
    executor.setWaitForTasksToCompleteOnShutdown(true);
    //初始化
    executor.initialize();
    return executor;
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    // 异步任务中异常处理
    return (arg0, arg1, arg2) -> {
      log.error("when execute '{}'  occurred an exception ", arg1.getName());
    };
  }

}
