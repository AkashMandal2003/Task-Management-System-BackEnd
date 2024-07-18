package com.akash.Task_Management_System.controller;


import com.akash.Task_Management_System.dto.TaskDto;
import com.akash.Task_Management_System.service.EmployeeService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/tasks")
    @PermitAll
    public ResponseEntity<List<TaskDto>> getTaskByUserId(){
        return ResponseEntity.ok(employeeService.getTaskByUserId());
    }

    @GetMapping("/task/{id}/{status}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @PathVariable String status){
        TaskDto updatedTask = employeeService.updateTask(id, status);
        if(updatedTask == null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updatedTask);
    }

}
