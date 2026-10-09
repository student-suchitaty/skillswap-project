package com.skillswap.backend.repository;

import com.skillswap.backend.model.SkillCatalog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SkillCatalogRepository
        extends MongoRepository<SkillCatalog, String> {

    List<SkillCatalog> findByStatus(String status);

    Optional<SkillCatalog> findByNameIgnoreCase(String name);
}