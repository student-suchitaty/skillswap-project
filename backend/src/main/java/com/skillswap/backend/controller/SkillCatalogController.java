package com.skillswap.backend.controller;

import com.skillswap.backend.model.SkillCatalog;
import com.skillswap.backend.service.SkillCatalogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/skills")
public class SkillCatalogController {

    @Autowired
    private SkillCatalogService skillCatalogService;

    // Get all approved skills
    @GetMapping("/approved")
    public List<SkillCatalog> getApprovedSkills() {

        return skillCatalogService.getApprovedSkills();

    }
}