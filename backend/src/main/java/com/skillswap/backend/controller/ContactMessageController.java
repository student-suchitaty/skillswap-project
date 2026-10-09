package com.skillswap.backend.controller;

import com.skillswap.backend.model.ContactMessage;
import com.skillswap.backend.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/contact")
@CrossOrigin("*")
public class ContactMessageController {

    @Autowired
    private ContactMessageRepository repository;


    // =========================
    // SUBMIT NEW MESSAGE
    // =========================

    @PostMapping
    public ContactMessage submitMessage(
            @RequestBody ContactMessage message) {

        message.setStatus("PENDING");

        message.setAdminResponse("");

        message.setResolvedAt("");

        return repository.save(message);
    }


    // =========================
    // GET ALL MESSAGES
    // USED BY ADMIN
    // =========================

    @GetMapping
    public List<ContactMessage> getMessages() {

        return repository.findAll();
    }


    // =========================
    // GET USER'S MESSAGES
    // =========================

    @GetMapping("/user")
    public List<ContactMessage> getUserMessages(
            @RequestParam String email) {

        return repository.findByUserEmail(email);
    }


    // =========================
    // RESOLVE MESSAGE
    // =========================

    @PutMapping("/{id}/resolve")
    public ContactMessage resolveMessage(
            @PathVariable String id,
            @RequestParam String adminResponse) {

        ContactMessage message = repository.findById(id)
                .orElseThrow();

        message.setAdminResponse(adminResponse);

        message.setStatus("RESOLVED");

        message.setResolvedAt(
                LocalDateTime.now().toString()
        );

        return repository.save(message);
    }


    // =========================
    // DELETE MESSAGE
    // =========================

    @DeleteMapping("/{id}")
    public void deleteMessage(
            @PathVariable String id) {

        repository.deleteById(id);
    }
}