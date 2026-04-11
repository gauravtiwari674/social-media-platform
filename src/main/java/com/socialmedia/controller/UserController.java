package com.socialmedia.controller;

import com.socialmedia.entity.User;
import com.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public java.util.Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        String token = userService.loginUser(username, password);
        java.util.Map<String, String> response = new java.util.HashMap<>();
        response.put("token", token);
        return response;
    }
}
