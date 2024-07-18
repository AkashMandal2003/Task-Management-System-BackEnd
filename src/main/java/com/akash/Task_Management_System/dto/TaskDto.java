package com.akash.Task_Management_System.dto;


import com.akash.Task_Management_System.enums.TaskStatus;
import lombok.Data;

import java.util.Date;

@Data
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private String priority;
    private TaskStatus taskStatus;
    private Long employeeId;
    private String employeeName;

}
