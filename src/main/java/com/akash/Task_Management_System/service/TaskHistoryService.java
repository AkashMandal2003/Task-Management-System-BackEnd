package com.akash.Task_Management_System.service;

import com.akash.Task_Management_System.dto.TaskHistoryDto;

import java.util.List;

public interface TaskHistoryService {

    List<TaskHistoryDto> getTaskHistory(Long taskId);

}
