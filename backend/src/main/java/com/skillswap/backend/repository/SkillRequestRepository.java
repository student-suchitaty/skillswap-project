package com.skillswap.backend.repository;

import com.skillswap.backend.model.SkillRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRequestRepository
        extends MongoRepository<SkillRequest, String> {


    List<SkillRequest> findByStatus(String status);

    List<SkillRequest> findByRequestedBy(String requestedBy);

    Optional<SkillRequest> findBySkillNameIgnoreCaseAndRequestedByAndStatus(
            String skillName,
            String requestedBy,
            String status
    );


}
