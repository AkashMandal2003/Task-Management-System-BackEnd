package com.akash.Task_Management_System.dto;

import com.akash.Task_Management_System.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;
    private Long userId;
    private UserRole userRole;

}
