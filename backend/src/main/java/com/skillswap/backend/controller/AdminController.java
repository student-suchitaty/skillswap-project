package com.skillswap.backend.controller;

import com.skillswap.backend.model.Request;
import com.skillswap.backend.model.Skill;
import com.skillswap.backend.model.StudyMaterial;
import com.skillswap.backend.model.User;
import com.skillswap.backend.repository.RequestRepository;
import com.skillswap.backend.repository.SkillRepository;
import com.skillswap.backend.repository.UserRepository;
import com.skillswap.backend.repository.StudyMaterialRepository;
import com.skillswap.backend.service.StudyMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    @Autowired private UserRepository userRepository;
    @Autowired private SkillRepository skillRepository;
    @Autowired private RequestRepository requestRepository;
    @Autowired private StudyMaterialRepository materialRepository;
    @Autowired private StudyMaterialService materialService;

    private void verifyAdmin(String adminEmail) {
        User user = userRepository.findByEmail(adminEmail);
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin access required.");
        }
    }

    @GetMapping("/users")
    public List<User> users(@RequestParam String adminEmail) { verifyAdmin(adminEmail); return userRepository.findAll(); }

    @GetMapping("/skills")
    public List<Skill> skills(@RequestParam String adminEmail) { verifyAdmin(adminEmail); return skillRepository.findAll(); }

    @GetMapping("/requests")
    public List<Request> requests(@RequestParam String adminEmail) { verifyAdmin(adminEmail); return requestRepository.findAll(); }

    @GetMapping("/materials")
    public List<StudyMaterial> materials(@RequestParam String adminEmail) { verifyAdmin(adminEmail); return materialRepository.findAll(); }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id, @RequestParam String adminEmail) { verifyAdmin(adminEmail); userRepository.deleteById(id); }

    @DeleteMapping("/requests/{id}")
    public void deleteRequest(@PathVariable String id, @RequestParam String adminEmail) { verifyAdmin(adminEmail); requestRepository.deleteById(id); }

    @DeleteMapping("/materials/{id}")
    public void deleteMaterial(@PathVariable String id, @RequestParam String adminEmail) { verifyAdmin(adminEmail); materialService.delete(id); }
}
