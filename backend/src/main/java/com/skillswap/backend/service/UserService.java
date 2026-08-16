package com.skillswap.backend.service;

import com.skillswap.backend.model.User;
import com.skillswap.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register User

    public User registerUser(User user){

        System.out.println("Saving User: " + user.getName());

        return userRepository.save(user);

    }

    // Login User

    public String loginUser(User user){

        User existingUser =
                userRepository.findByEmail(user.getEmail());

        if(existingUser == null){

            return "User Not Found";

        }

        if(existingUser.getPassword()
                .equals(user.getPassword())){

            return "Login Successful";

        }

        return "Invalid Password";

    }
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email);

    }

}