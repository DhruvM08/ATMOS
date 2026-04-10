package com.atmos.auth_service.controller;

import com.atmos.auth_service.model.*;
import com.atmos.auth_service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    @CircuitBreaker(name = "auth", fallbackMethod = "fallbackSignup")
    public ResponseEntity<String> signupUser(@RequestBody SignupDTO user) {
        String result = userService.registerUser(user);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<String> fallbackSignup(SignupDTO user, Exception e) {
        return new ResponseEntity<>("Signup service is currently unavailable.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @PostMapping("/signin")
    @CircuitBreaker(name = "auth", fallbackMethod = "fallbackSignin")
    public ResponseEntity<LoginDTO> login(@RequestBody UserEntity userEntity, HttpServletResponse response) {
        LoginDTO result = userService.loginUser(userEntity);
        if (result != null && result.getToken() != null) {
            jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("token", result.getToken());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(30 * 60 * 60);
            response.addCookie(cookie);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<LoginDTO> fallbackSignin(UserEntity userEntity, HttpServletResponse response, Exception e) {
        return new ResponseEntity<>(new LoginDTO(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @PostMapping("/profile")
    @CircuitBreaker(name = "auth", fallbackMethod = "fallbackProfile")
    public ResponseEntity<UserProfile> updateProfile(@RequestParam String emailId, @RequestBody UserProfile userProfile) {
        userService.updateUserProfile(emailId, userProfile);
        return ResponseEntity.ok(userProfile);
    }

    public ResponseEntity<UserProfile> fallbackProfile(String emailId, UserProfile userProfile, Exception e) {
        return new ResponseEntity<>(new UserProfile(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping("/getusers")
    @CircuitBreaker(name = "auth", fallbackMethod = "fallbackGetUsers")
    public ResponseEntity<List<UserEntity>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    public ResponseEntity<List<UserEntity>> fallbackGetUsers(Exception e) {
        return new ResponseEntity<>(List.of(), HttpStatus.SERVICE_UNAVAILABLE);
    }
    @GetMapping("/getusers/city")
    public ResponseEntity<List<UserEntity>> getUsersByCity(@RequestParam String city) {
        return ResponseEntity.ok(userService.getUsersByCity(city));
    }
}
