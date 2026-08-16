package com.skillswap.backend.service;

import com.skillswap.backend.model.Skill;
import com.skillswap.backend.repository.SkillRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    // Add Skill

    public Skill addSkill(Skill skill){

        return skillRepository.save(skill);

    }

    // Find Matches

    public List<Skill> findMatches(String skill){

        return skillRepository
                .findByWantedSkill(skill);

    }

}