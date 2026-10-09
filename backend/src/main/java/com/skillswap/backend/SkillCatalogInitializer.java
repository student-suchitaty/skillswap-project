package com.skillswap.backend;

import com.skillswap.backend.model.SkillCatalog;
import com.skillswap.backend.repository.SkillCatalogRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SkillCatalogInitializer {

    @Bean
    CommandLineRunner initializeSkills(SkillCatalogRepository repository) {

        return args -> {

            if (repository.count() > 0) {
                return;
            }

            List<SkillCatalog> skills = Arrays.asList(

                    // Programming & Development
                    createSkill("Java", "Programming"),
                    createSkill("Python", "Programming"),
                    createSkill("C", "Programming"),
                    createSkill("C++", "Programming"),
                    createSkill("C#", "Programming"),
                    createSkill("JavaScript", "Programming"),
                    createSkill("TypeScript", "Programming"),
                    createSkill("HTML", "Web Development"),
                    createSkill("CSS", "Web Development"),
                    createSkill("React", "Web Development"),
                    createSkill("Angular", "Web Development"),
                    createSkill("Node.js", "Web Development"),
                    createSkill("Spring Boot", "Java Development"),
                    createSkill("Spring Framework", "Java Development"),
                    createSkill("JSP", "Java Development"),
                    createSkill("Servlets", "Java Development"),
                    createSkill("JDBC", "Java Development"),
                    createSkill("REST API", "Web Development"),
                    createSkill("Full-Stack Development", "Development"),
                    createSkill("Front-End Development", "Development"),
                    createSkill("Back-End Development", "Development"),

                    // Database & Data
                    createSkill("SQL", "Database"),
                    createSkill("MySQL", "Database"),
                    createSkill("PostgreSQL", "Database"),
                    createSkill("MongoDB", "Database"),
                    createSkill("Database Management", "Database"),
                    createSkill("Data Structures", "Computer Science"),
                    createSkill("Algorithms", "Computer Science"),
                    createSkill("Data Analysis", "Data"),
                    createSkill("Data Visualization", "Data"),

                    // Cloud & DevOps
                    createSkill("Cloud Computing", "Cloud"),
                    createSkill("AWS", "Cloud"),
                    createSkill("Microsoft Azure", "Cloud"),
                    createSkill("Google Cloud", "Cloud"),
                    createSkill("Docker", "DevOps"),
                    createSkill("Kubernetes", "DevOps"),
                    createSkill("Git", "DevOps"),
                    createSkill("GitHub", "DevOps"),
                    createSkill("CI/CD", "DevOps"),
                    createSkill("Jenkins", "DevOps"),
                    createSkill("Linux", "Operating Systems"),
                    createSkill("DevOps", "DevOps"),

                    // Cybersecurity
                    createSkill("Cybersecurity", "Cybersecurity"),
                    createSkill("Network Security", "Cybersecurity"),
                    createSkill("Web Security", "Cybersecurity"),
                    createSkill("Ethical Hacking", "Cybersecurity"),
                    createSkill("Information Security", "Cybersecurity"),
                    createSkill("Cryptography", "Cybersecurity"),
                    createSkill("Security Testing", "Cybersecurity"),
                    createSkill("Cyber Threat Awareness", "Cybersecurity"),

                    // AI & ChatGPT
                    createSkill("Artificial Intelligence", "AI"),
                    createSkill("Generative AI", "AI"),
                    createSkill("Machine Learning", "AI"),
                    createSkill("Deep Learning", "AI"),
                    createSkill("ChatGPT", "AI"),
                    createSkill("Prompt Engineering", "AI"),
                    createSkill("AI Tools", "AI"),
                    createSkill("AI-Assisted Coding", "AI"),
                    createSkill("AI Content Creation", "AI"),
                    createSkill("Large Language Models (LLMs)", "AI"),

                    // Professional Skills
                    createSkill("Project Management", "Professional"),
                    createSkill("Agile", "Professional"),
                    createSkill("Scrum", "Professional"),
                    createSkill("Software Development Life Cycle (SDLC)", "Professional"),
                    createSkill("Problem Solving", "Professional"),
                    createSkill("Time Management", "Professional"),
                    createSkill("Team Management", "Professional"),
                    createSkill("Leadership", "Professional"),
                    createSkill("Decision Making", "Professional"),
                    createSkill("Critical Thinking", "Professional"),
                    createSkill("Adaptability", "Professional"),

                    // Soft Skills
                    createSkill("Communication", "Soft Skills"),
                    createSkill("Verbal Communication", "Soft Skills"),
                    createSkill("Written Communication", "Soft Skills"),
                    createSkill("Presentation Skills", "Soft Skills"),
                    createSkill("Public Speaking", "Soft Skills"),
                    createSkill("Teamwork", "Soft Skills"),
                    createSkill("Collaboration", "Soft Skills"),
                    createSkill("Interpersonal Skills", "Soft Skills"),
                    createSkill("Active Listening", "Soft Skills"),
                    createSkill("Negotiation", "Soft Skills"),
                    createSkill("Conflict Resolution", "Soft Skills"),
                    createSkill("Creativity", "Soft Skills"),
                    createSkill("Emotional Intelligence", "Soft Skills"),
                    createSkill("Work Ethics", "Soft Skills"),
                    createSkill("Professionalism", "Soft Skills"),
                    createSkill("Self-Motivation", "Soft Skills"),
                    createSkill("Confidence", "Soft Skills"),
                    createSkill("Patience", "Soft Skills"),
                    createSkill("Responsibility", "Soft Skills"),
                    createSkill("Accountability", "Soft Skills"),
                    createSkill("Networking", "Soft Skills"),
                    createSkill("Stress Management", "Soft Skills"),
                    createSkill("Flexibility", "Soft Skills"),
                    createSkill("Learning Ability", "Soft Skills"),
                    createSkill("Attention to Detail", "Soft Skills"),

                    // Design & Other
                    createSkill("UI/UX Design", "Design"),
                    createSkill("Graphic Design", "Design"),
                    createSkill("Canva", "Design"),
                    createSkill("Web Design", "Design"),
                    createSkill("Content Writing", "Creative"),
                    createSkill("Technical Writing", "Creative"),
                    createSkill("Digital Marketing", "Marketing"),
                    createSkill("SEO", "Marketing"),
                    createSkill("Video Editing", "Creative"),
                    createSkill("Photography", "Creative")
            );

            repository.saveAll(skills);

            System.out.println("Approved skill catalog initialized successfully.");
        };
    }

    private SkillCatalog createSkill(String name, String category) {

        SkillCatalog skill = new SkillCatalog();

        skill.setName(name);
        skill.setCategory(category);
        skill.setStatus("APPROVED");

        return skill;
    }
}