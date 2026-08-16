package com.skillswap.backend.controller;

import com.skillswap.backend.model.User;
import com.skillswap.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")

    public User registerUser(
            @RequestBody User user){

        System.out.println(user);

        return userService.registerUser(user);

    }

    @PostMapping("/login")

    public String loginUser(
            @RequestBody User user){

        return userService.loginUser(user);

    }

    @GetMapping("/user/{email}")

    public User getUser(
            @PathVariable String email) {

        return userService
                .getUserByEmail(email);

    }

}