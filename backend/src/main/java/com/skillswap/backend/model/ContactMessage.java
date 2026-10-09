package com.skillswap.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "contactMessages")
public class ContactMessage {

    @Id
    private String id;

    private String userEmail;

    private String category;

    private String subject;

    private String message;

    private String status;

    private String adminResponse;

    private String resolvedAt;


    // =========================
    // DEFAULT CONSTRUCTOR
    // =========================

    public ContactMessage() {
    }


    // =========================
    // ID
    // =========================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    // =========================
    // USER EMAIL
    // =========================

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    // =========================
    // CATEGORY
    // =========================

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    // =========================
    // SUBJECT
    // =========================

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    // =========================
    // MESSAGE
    // =========================

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    // =========================
    // STATUS
    // =========================

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    // =========================
    // ADMIN RESPONSE
    // =========================

    public String getAdminResponse() {
        return adminResponse;
    }

    public void setAdminResponse(String adminResponse) {
        this.adminResponse = adminResponse;
    }


    // =========================
    // RESOLVED TIME
    // =========================

    public String getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(String resolvedAt) {
        this.resolvedAt = resolvedAt;
    }
}