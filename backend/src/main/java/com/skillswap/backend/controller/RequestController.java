package com.skillswap.backend.controller;

import com.skillswap.backend.model.Request;
import com.skillswap.backend.model.User;
import com.skillswap.backend.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping("/send-request")
    public ResponseEntity<?> sendRequest(@RequestBody Request request) {
        try {
            return ResponseEntity.ok(requestService.sendRequest(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/my-requests/{email}")
    public List<Request> getMyRequests(@PathVariable String email) {
        return requestService.getMyRequests(email);
    }

    @GetMapping("/connections/{email}")
    public List<User> getConnections(@PathVariable String email) {
        return requestService.getConnections(email);
    }

    @PutMapping("/requests/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable String id,
            @RequestParam String status,
            @RequestParam String receiverEmail) {
        try {
            return ResponseEntity.ok(requestService.updateStatus(id, status, receiverEmail));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
