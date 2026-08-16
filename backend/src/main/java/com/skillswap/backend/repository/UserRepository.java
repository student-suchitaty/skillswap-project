package com.skillswap.backend.repository;

import com.skillswap.backend.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository
        extends MongoRepository<User, String> {

    User findByEmail(String email);

}