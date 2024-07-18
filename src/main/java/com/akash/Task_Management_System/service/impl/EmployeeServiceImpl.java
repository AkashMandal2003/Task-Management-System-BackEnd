package com.akash.Task_Management_System.service.impl;

import com.akash.Task_Management_System.dto.TaskDto;
import com.akash.Task_Management_System.entity.Task;
import com.akash.Task_Management_System.entity.User;
import com.akash.Task_Management_System.enums.TaskStatus;
import com.akash.Task_Management_System.repository.TaskRepository;
import com.akash.Task_Management_System.service.EmployeeService;
import com.akash.Task_Management_System.utils.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final TaskRepository taskRepository;

    private final JwtUtil jwtUtil;

    @Override
    public List<TaskDto> getTaskByUserId() {
        User loggedInUser = jwtUtil.getLoggedInUser();
        if(loggedInUser!=null){
            return taskRepository.findAllByUserId(loggedInUser.getId())
                    .stream()
                    .sorted(Comparator.comparing(Task::getDueDate).reversed())
                    .map(Task::getTaskDto)
                    .collect(Collectors.toList());
        }
        throw new EntityNotFoundException("User Not Found");
    }

    @Override
    public TaskDto updateTask(Long id, String status) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task exitingTask=optionalTask.get();
            exitingTask.setTaskStatus(mapStringToTaskStatus(status));

            return taskRepository.save(exitingTask).getTaskDto();
        }
        throw new EntityNotFoundException("Task Not Found");
    }

    private TaskStatus mapStringToTaskStatus(String status){
        return switch (status){
            case "PENDING" -> TaskStatus.PENDING;
            case "CANCELLED" -> TaskStatus.CANCELLED;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            case "DEFERRED" -> TaskStatus.DEFERRED;

            default -> TaskStatus.INPROGRESS;
        };
    }
}
