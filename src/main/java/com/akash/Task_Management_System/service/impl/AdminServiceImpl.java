package com.akash.Task_Management_System.service.impl;

import com.akash.Task_Management_System.dto.TaskDto;
import com.akash.Task_Management_System.dto.UserDto;
import com.akash.Task_Management_System.entity.Task;
import com.akash.Task_Management_System.entity.TaskHistory;
import com.akash.Task_Management_System.entity.User;
import com.akash.Task_Management_System.enums.TaskStatus;
import com.akash.Task_Management_System.enums.UserRole;
import com.akash.Task_Management_System.repository.TaskHistoryRepository;
import com.akash.Task_Management_System.repository.TaskRepository;
import com.akash.Task_Management_System.repository.UserRepository;
import com.akash.Task_Management_System.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final TaskHistoryRepository taskHistoryRepository;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().
                stream()
                .filter(user -> user.getUserRole()== UserRole.EMPLOYEE)
                .map(User::getUserDto).collect(Collectors.toList());
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Optional<User> optionalUser=userRepository.findById(taskDto.getEmployeeId());
        if(optionalUser.isPresent()){
            Task task=new Task();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setPriority(taskDto.getPriority());
            task.setDueDate(taskDto.getDueDate());
            task.setTaskStatus(TaskStatus.INPROGRESS);
            task.setUser(optionalUser.get());

            return taskRepository.save(task).getTaskDto();
        }
        return null;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate).reversed())
                .map(Task::getTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Optional<Task> optionalTask=taskRepository.findById(id);
        return optionalTask.map((Task::getTaskDto)).orElse(null);
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Optional<Task> optionalTask=taskRepository.findById(id);
        Optional<User> optionalUser=userRepository.findById(taskDto.getEmployeeId());
        if(optionalTask.isPresent() && optionalUser.isPresent()){
            Task existingTask=optionalTask.get();
            TaskStatus oldStatus = existingTask.getTaskStatus();

            existingTask.setTitle(taskDto.getTitle());
            existingTask.setDescription(taskDto.getDescription());
            existingTask.setDueDate(taskDto.getDueDate());
            existingTask.setPriority(taskDto.getPriority());
            existingTask.setTaskStatus(mapStringToTaskStatus(String.valueOf(taskDto.getTaskStatus())));
            existingTask.setUser(optionalUser.get());

            TaskHistory taskHistory = new TaskHistory();
            taskHistory.setTask(existingTask);
            taskHistory.setAction("Task updated: Status changed from " + oldStatus + " to " + existingTask.getTaskStatus()+
                    ", Due date changed to "+existingTask.getDueDate()+", Priority changed to "+existingTask.getPriority()+".");
            taskHistory.setTimestamp(LocalDateTime.now());

            taskHistoryRepository.save(taskHistory);

            return taskRepository.save(existingTask).getTaskDto();
        }
        return null;
    }

    @Override
    public List<TaskDto> searchTaskByTitle(String title) {
        return taskRepository.findAllByTitleContaining(title)
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate).reversed())
                .map(Task::getTaskDto)
                .collect(Collectors.toList());
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
