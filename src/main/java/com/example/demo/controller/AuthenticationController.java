package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
  private final UserService userService;

  public AuthenticationController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody User user) {
    return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<User> login(@RequestBody User loginData) {
    Optional<User> userOpt = userService.authenticate(loginData.getUsername(), loginData.getPassword());
    if (userOpt.isPresent()) {
      return new ResponseEntity<>(userOpt.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }
}

