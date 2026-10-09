package com.skillswap.backend.repository;

import com.skillswap.backend.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface UserRepository
        extends MongoRepository<User, String> {

    User findByEmail(String email);

    List<User> findByEmailIn(List<String> emails);

}