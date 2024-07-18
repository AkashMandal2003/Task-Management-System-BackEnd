package com.akash.Task_Management_System.service.impl;

import com.akash.Task_Management_System.dto.TaskHistoryDto;
import com.akash.Task_Management_System.entity.TaskHistory;
import com.akash.Task_Management_System.repository.TaskHistoryRepository;
import com.akash.Task_Management_System.service.TaskHistoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskHistoryServiceImpl implements TaskHistoryService {

    private final TaskHistoryRepository taskHistoryRepository;

    @Override
    public List<TaskHistoryDto> getTaskHistory(Long taskId) {
        List<TaskHistory> taskHistories = taskHistoryRepository.findByTaskId(taskId);

        if (taskHistories.isEmpty()) {
            throw new EntityNotFoundException("Task history not found for taskId: " + taskId);
        }

        return taskHistories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TaskHistoryDto convertToDto(TaskHistory taskHistory) {
        TaskHistoryDto dto = new TaskHistoryDto();
        dto.setId(taskHistory.getId());
        dto.setTaskId(taskHistory.getTask().getId());
        dto.setAction(taskHistory.getAction());
        dto.setTimestamp(taskHistory.getTimestamp());
        return dto;
    }
}
