package com.akash.Task_Management_System.dto;

import com.akash.Task_Management_System.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;
}
