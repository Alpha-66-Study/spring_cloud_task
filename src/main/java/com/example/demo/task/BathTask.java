package com.example.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@EnableTask
@Slf4j
public class BathTask implements CommandLineRunner {
  @Autowired
  private SimpleTask simpleTask; // 注入任务监听器

  @Override
  public void run(String... args) throws Exception {
    log.info("=== 批处理任务开始 ===");
    // 模拟处理100条数据
    AtomicInteger successCount = new AtomicInteger(0);
    AtomicInteger failCount = new AtomicInteger(0);

    for (int i = 0; i < 10; i++) {
      try {
        // 模拟处理逻辑
        processData(i);
        successCount.incrementAndGet();
      } catch (Exception e) {
        log.error("处理数据失败: {}", i, e);
        failCount.incrementAndGet();
      }
    }

    log.info("=== 批处理任务完成 ===");
    log.info("成功: {}, 失败: {}", successCount.get(), failCount.get());
  }

  private void processData(int index) throws InterruptedException {
    log.info("处理数据项: {}", index);
    Thread.sleep(100);
  }
}
