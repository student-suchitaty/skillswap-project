package com.skillswap.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "skills")

public class Skill {

    @Id
    private String id;

    private String email;

    private String offeredSkill;

    private String wantedSkill;

    // Getter and Setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfferedSkill() {
        return offeredSkill;
    }

    public void setOfferedSkill(String offeredSkill) {
        this.offeredSkill = offeredSkill;
    }

    public String getWantedSkill() {
        return wantedSkill;
    }

    public void setWantedSkill(String wantedSkill) {
        this.wantedSkill = wantedSkill;
    }

}