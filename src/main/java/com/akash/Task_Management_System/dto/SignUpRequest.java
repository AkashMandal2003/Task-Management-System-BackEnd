package com.akash.Task_Management_System.dto;


import lombok.Data;

@Data
public class SignUpRequest {

    private String name;
    private String email;
    private String password;

}
