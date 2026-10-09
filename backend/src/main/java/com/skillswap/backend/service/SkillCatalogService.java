package com.skillswap.backend.service;

import com.skillswap.backend.model.SkillCatalog;
import com.skillswap.backend.repository.SkillCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillCatalogService {

    @Autowired
    private SkillCatalogRepository skillCatalogRepository;

    // Get all approved skills
    public List<SkillCatalog> getApprovedSkills() {
        return skillCatalogRepository.findByStatus("APPROVED");
    }
}
