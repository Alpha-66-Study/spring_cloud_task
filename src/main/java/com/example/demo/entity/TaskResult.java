package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "task_result")
public class TaskResult {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "task_name",nullable = false)
  private String taskName;

  @Column(name="execution_id")
  private Long executionId;

  @Column(name = "result_data")
  private String resultData;

  @Column(name = "create_time")
  private LocalDateTime createTime;
}
