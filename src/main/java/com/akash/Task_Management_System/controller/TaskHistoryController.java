package com.akash.Task_Management_System.controller;

import com.akash.Task_Management_System.dto.TaskHistoryDto;
import com.akash.Task_Management_System.service.TaskHistoryService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TaskHistoryController {

    private final TaskHistoryService taskHistoryService;

    @GetMapping("admin/history/{id}")
    public ResponseEntity<List<TaskHistoryDto>> getTaskHistory(@PathVariable(name = "id") Long id) {
        List<TaskHistoryDto> taskHistory = taskHistoryService.getTaskHistory(id);
        return ResponseEntity.ok(taskHistory);
    }

    @GetMapping("employee/history/{id}")
    public ResponseEntity<List<TaskHistoryDto>> getTaskHistoryy(@PathVariable(name = "id") Long id) {
        List<TaskHistoryDto> taskHistory = taskHistoryService.getTaskHistory(id);
        return ResponseEntity.ok(taskHistory);
    }


}
