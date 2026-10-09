package com.skillswap.backend;

import com.skillswap.backend.model.User;
import com.skillswap.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner createDefaultAdmin(UserRepository userRepository) {
        return args -> {
            User admin = userRepository.findByEmail("admin@skillswap.com");
            if (admin == null) {
                admin = new User();
                admin.setName("SkillSwap Admin");
                admin.setEmail("admin@skillswap.com");
                admin.setPassword("admin123");
                admin.setRole("ADMIN");
                userRepository.save(admin);
            } else if (!"ADMIN".equalsIgnoreCase(admin.getRole())) {
                admin.setRole("ADMIN");
                userRepository.save(admin);
            }
        };
    }
}
