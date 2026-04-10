package com.socialmedia.auth.service;

import com.socialmedia.auth.LoginRequest;
import com.socialmedia.auth.RegisterRequest;
import com.socialmedia.user.entity.User;
import com.socialmedia.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public String register(RegisterRequest request) {

        User user = new User();
        user.setUserID((int)(Math.random()*10000));
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        userRepository.save(user);

        return "User Registered Successfully";
    }

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail());

        if(user == null){
            return "User not found";
        }

        if(!user.getPassword().equals(request.getPassword())){
            return "Invalid password";
        }

        return "Login Successful";
    }
}