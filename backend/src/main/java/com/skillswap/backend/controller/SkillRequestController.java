
package com.skillswap.backend.controller;

import com.skillswap.backend.model.SkillRequest;
import com.skillswap.backend.model.User;
import com.skillswap.backend.repository.UserRepository;
import com.skillswap.backend.service.SkillRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/skill-requests")
public class SkillRequestController {

    @Autowired
    private SkillRequestService skillRequestService;

    @Autowired
    private UserRepository userRepository;

    // Verify that the account has the ADMIN role
    private void verifyAdmin(String adminEmail) {
        User user = userRepository.findByEmail(adminEmail);

        if (user == null ||
                !"ADMIN".equalsIgnoreCase(user.getRole())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Admin access required."
            );
        }
    }

    // Submit a new skill request
    @PostMapping
    public SkillRequest submitRequest(
            @RequestBody SkillRequest request) {
        return skillRequestService.submitRequest(request);
    }

    // Get all pending skill requests
    @GetMapping("/pending")
    public List<SkillRequest> getPendingRequests() {
        return skillRequestService.getPendingRequests();
    }

    // Get requests submitted by a user
    @GetMapping("/user")
    public List<SkillRequest> getUserRequests(
            @RequestParam String email) {
        return skillRequestService.getUserRequests(email);
    }

    // Approve a skill request
    @PutMapping("/{id}/approve")
    public SkillRequest approveRequest(
            @PathVariable String id,
            @RequestParam String adminEmail) {

        verifyAdmin(adminEmail);
        return skillRequestService.approveRequest(id);
    }

    // Reject a skill request
    @PutMapping("/{id}/reject")
    public SkillRequest rejectRequest(
            @PathVariable String id,
            @RequestParam String adminEmail) {

        verifyAdmin(adminEmail);
        return skillRequestService.rejectRequest(id);
    }
}