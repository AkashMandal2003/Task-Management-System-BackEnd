package com.akash.Task_Management_System.entity;

import com.akash.Task_Management_System.dto.TaskDto;
import com.akash.Task_Management_System.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Date dueDate;
    private String priority;
    private TaskStatus taskStatus;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskHistory> history;

    public TaskDto getTaskDto(){
        TaskDto taskDto=new TaskDto();
        taskDto.setId(id);
        taskDto.setTitle(title);
        taskDto.setDescription(description);
        taskDto.setEmployeeName(user.getName());
        taskDto.setEmployeeId(user.getId());
        taskDto.setTaskStatus(taskStatus);
        taskDto.setDueDate(dueDate);
        taskDto.setPriority(priority);

        return taskDto;
    }


}
