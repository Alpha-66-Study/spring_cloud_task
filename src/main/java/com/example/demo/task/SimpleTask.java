package com.example.demo.task;


import com.example.demo.entity.TaskResult;
import com.example.demo.repository.TaskResultRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.listener.TaskExecutionListener;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@Slf4j
public class SimpleTask implements TaskExecutionListener {

  @Autowired
  private TaskResultRepository taskResultRepository;

  @Override
  public void onTaskEnd(TaskExecution taskExecution){
    log.info("任务结束: {}", taskExecution.getTaskName());

    TaskResult result = new TaskResult();
    result.setTaskName(taskExecution.getTaskName());
    result.setExecutionId(taskExecution.getExecutionId());
    result.setCreateTime(LocalDateTime.now());

    Long startInstant = taskExecution.getStartTime() != null ? taskExecution.getStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() : null;
    Long endInstant = taskExecution.getEndTime() != null ? taskExecution.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() : null;

    if (startInstant == null || endInstant == null) {
      result.setResultData("任务执行时间信息缺失");
    } else {
      long durationMillis = endInstant - startInstant;
      double durationSeconds = durationMillis / 1000.0;
      result.setResultData(String.format("任务执行成功，耗时: %.3fs", durationSeconds));
    }

    taskResultRepository.save(result);
    log.info("任务结果已保存，ID: {}", result.getId());
  }

  @Override
  public void onTaskStartup(TaskExecution taskExecution) {
    log.info("任务开始: {}", taskExecution.getTaskName());
  }
  @Override
  public void onTaskFailed(TaskExecution taskExecution, Throwable throwable) {
    log.error("任务失败: {}", taskExecution.getTaskName(), throwable);
  }
}
