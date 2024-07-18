package com.akash.Task_Management_System.service;

import com.akash.Task_Management_System.dto.TaskDto;
import com.akash.Task_Management_System.dto.UserDto;

import java.util.List;

public interface AdminService {

    List<UserDto> getUsers();

    TaskDto createTask(TaskDto taskDto);

    List<TaskDto> getAllTasks();

    void deleteTask(Long id);

    TaskDto getTaskById(Long id);

    TaskDto updateTask(Long id, TaskDto taskDto);

    List<TaskDto> searchTaskByTitle(String title);

}
