package com.skillswap.backend.service;

import com.skillswap.backend.model.SkillCatalog;
import com.skillswap.backend.model.SkillRequest;
import com.skillswap.backend.repository.SkillCatalogRepository;
import com.skillswap.backend.repository.SkillRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SkillRequestService {


    @Autowired
    private SkillRequestRepository skillRequestRepository;

    @Autowired
    private SkillCatalogRepository skillCatalogRepository;

    // Submit a new skill request
    public SkillRequest submitRequest(SkillRequest request) {

        if (request == null
                || request.getSkillName() == null
                || request.getSkillName().trim().isEmpty()
                || request.getRequestedBy() == null
                || request.getRequestedBy().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Skill name and requester email are required."
            );
        }

        String skillName = request.getSkillName().trim();
        String requestedBy = request.getRequestedBy().trim();

        // Prevent requesting a skill that is already approved
        if (skillCatalogRepository
                .findByNameIgnoreCase(skillName)
                .filter(skill ->
                        "APPROVED".equalsIgnoreCase(skill.getStatus()))
                .isPresent()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "This skill is already approved and available."
            );
        }

        // Prevent duplicate pending requests from the same user
        if (skillRequestRepository
                .findBySkillNameIgnoreCaseAndRequestedByAndStatus(
                        skillName,
                        requestedBy,
                        "PENDING"
                )
                .isPresent()) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "You already have a pending request for this skill."
            );
        }

        request.setSkillName(skillName);
        request.setRequestedBy(requestedBy);
        request.setStatus("PENDING");

        return skillRequestRepository.save(request);
    }

    // Get all pending skill requests
    public List<SkillRequest> getPendingRequests() {
        return skillRequestRepository.findByStatus("PENDING");
    }

    // Get requests submitted by a user
    public List<SkillRequest> getUserRequests(String email) {
        return skillRequestRepository.findByRequestedBy(email);
    }

    // Approve a skill request
    public SkillRequest approveRequest(String id) {

        SkillRequest request =
                skillRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Skill request not found.")
                        );

        // Add the skill to the catalog if it doesn't already exist
        if (skillCatalogRepository
                .findByNameIgnoreCase(request.getSkillName())
                .isEmpty()) {

            SkillCatalog skill = new SkillCatalog();
            skill.setName(request.getSkillName());
            skill.setCategory("User Requested");
            skill.setStatus("APPROVED");

            skillCatalogRepository.save(skill);
        }

        request.setStatus("APPROVED");

        return skillRequestRepository.save(request);
    }

    // Reject a skill request
    public SkillRequest rejectRequest(String id) {

        SkillRequest request =
                skillRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Skill request not found.")
                        );

        request.setStatus("REJECTED");

        return skillRequestRepository.save(request);
    }


}
