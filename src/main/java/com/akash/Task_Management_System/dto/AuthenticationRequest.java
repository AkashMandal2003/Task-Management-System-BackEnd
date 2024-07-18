package com.akash.Task_Management_System.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;
    private String password;

}
