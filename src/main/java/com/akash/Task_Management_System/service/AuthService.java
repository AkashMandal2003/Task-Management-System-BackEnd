package com.akash.Task_Management_System.service;

import com.akash.Task_Management_System.dto.SignUpRequest;
import com.akash.Task_Management_System.dto.UserDto;

public interface AuthService {
    UserDto signupUser(SignUpRequest signUpRequest);

    boolean hasUserWithEmail(String email);
}
