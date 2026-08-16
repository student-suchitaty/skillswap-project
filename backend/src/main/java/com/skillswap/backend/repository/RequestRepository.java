package com.skillswap.backend.repository;

import com.skillswap.backend.model.Request;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RequestRepository
        extends MongoRepository<Request, String> {

    List<Request> findByReceiverEmail(
            String receiverEmail
    );

}