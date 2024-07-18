package com.akash.Task_Management_System.controller;

import com.akash.Task_Management_System.dto.AuthenticationRequest;
import com.akash.Task_Management_System.dto.AuthenticationResponse;
import com.akash.Task_Management_System.dto.SignUpRequest;
import com.akash.Task_Management_System.dto.UserDto;
import com.akash.Task_Management_System.entity.User;
import com.akash.Task_Management_System.repository.UserRepository;
import com.akash.Task_Management_System.service.AuthService;
import com.akash.Task_Management_System.service.UserService;
import com.akash.Task_Management_System.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignUpRequest signUpRequest){
        if(authService.hasUserWithEmail(signUpRequest.getEmail())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exist with this email");
        }
        UserDto createdUserDto = authService.signupUser(signUpRequest);
        if(createdUserDto==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Not created");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username and password");
        }
        final UserDetails userDetails=userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String generatedToken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        if(optionalUser.isPresent()) {
            authenticationResponse.setJwt(generatedToken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;
    }
}
