package com.skillswap.backend.repository;

import com.skillswap.backend.model.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SkillRepository
        extends MongoRepository<Skill, String> {

    List<Skill> findByOfferedSkill(String offeredSkill);

}

