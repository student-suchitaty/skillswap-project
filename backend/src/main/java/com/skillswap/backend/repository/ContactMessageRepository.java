package com.skillswap.backend.repository;

import com.skillswap.backend.model.ContactMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContactMessageRepository
        extends MongoRepository<ContactMessage, String> {

    List<ContactMessage> findByUserEmail(String userEmail);

}