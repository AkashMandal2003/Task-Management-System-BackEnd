package com.akash.Task_Management_System.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskHistoryDto {
    private Long id;
    private Long taskId;
    private String action;
    private LocalDateTime timestamp;
}
