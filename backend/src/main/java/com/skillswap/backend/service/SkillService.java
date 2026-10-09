
package com.skillswap.backend.service;

import com.skillswap.backend.model.Skill;
import com.skillswap.backend.model.SkillCatalog;
import com.skillswap.backend.repository.SkillRepository;
import com.skillswap.backend.repository.SkillCatalogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillCatalogRepository skillCatalogRepository;

    // Check whether a skill is approved
    private boolean isApprovedSkill(String skillName) {

        if (skillName == null || skillName.trim().isEmpty()) {
            return false;
        }

        Optional<SkillCatalog> skill =
                skillCatalogRepository.findByNameIgnoreCase(
                        skillName.trim()
                );

        return skill.isPresent()
                && "APPROVED".equalsIgnoreCase(
                skill.get().getStatus()
        );
    }

    // Add Skill with backend validation
    public Skill addSkill(Skill skill) {

        if (skill == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Skill details are required."
            );
        }

        if (!isApprovedSkill(skill.getOfferedSkill())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The skill you can teach is not approved."
            );
        }

        if (!isApprovedSkill(skill.getWantedSkill())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The skill you want to learn is not approved."
            );
        }

        return skillRepository.save(skill);
    }

    // Find Matches
    public List<Skill> findMatches(String skill) {

        return skillRepository.findByOfferedSkill(skill);
    }


    // Get All Skills
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }


}