package com.akash.Task_Management_System.service;

import com.akash.Task_Management_System.dto.TaskDto;

import java.util.List;

public interface EmployeeService {

    List<TaskDto> getTaskByUserId();

    TaskDto updateTask(Long id, String status);
}
