package com.skillswap.backend.controller;

import com.skillswap.backend.model.Skill;
import com.skillswap.backend.service.SkillService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class SkillController {

    @Autowired
    private SkillService skillService;

    // Add Skill
    @PostMapping("/addSkill")
    public Skill addSkill(@RequestBody Skill skill) {
        return skillService.addSkill(skill);
    }

    // Find Matches
    @GetMapping("/findMatches/{skill}")
    public List<Skill> findMatches(@PathVariable String skill) {
        return skillService.findMatches(skill);
    }

    // Get All Skills
    @GetMapping("/skills")
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }
}

