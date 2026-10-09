package com.skillswap.backend.service;

import com.skillswap.backend.model.User;
import com.skillswap.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        }
        return userRepository.save(user);
    }

    public String loginUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null) return "User Not Found";
        if (!existingUser.getPassword().equals(user.getPassword())) return "Invalid Password";
        return "ADMIN".equalsIgnoreCase(existingUser.getRole())
                ? "Admin Login Successful" : "Login Successful";
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
