package com.skillswap.backend.controller;

import com.skillswap.backend.model.StudyMaterial;
import com.skillswap.backend.service.StudyMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
public class StudyMaterialController {
    @Autowired private StudyMaterialService service;
    @Autowired private GridFsTemplate gridFsTemplate;

    @PostMapping(value = "/study-materials/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> send(
            @RequestParam String senderEmail,
            @RequestParam String receiverEmail,
            @RequestParam String title,
            @RequestParam(required = false, defaultValue = "") String description,
            @RequestPart MultipartFile file) {
        try { return ResponseEntity.ok(service.send(senderEmail, receiverEmail, title, description, file)); }
        catch (IllegalArgumentException | IOException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @GetMapping("/study-materials/received/{email}")
    public List<StudyMaterial> received(@PathVariable String email) { return service.received(email); }

    @GetMapping("/study-materials/sent/{email}")
    public List<StudyMaterial> sent(@PathVariable String email) { return service.sent(email); }

    @GetMapping("/study-materials/download/{id}")
    public ResponseEntity<InputStreamResource> download(@PathVariable String id) throws IOException {
        StudyMaterial material = service.find(id);
        GridFsResource resource = gridFsTemplate.getResource(
                gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(new ObjectId(material.getFileId())))));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(material.getContentType() == null ? "application/octet-stream" : material.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + material.getFileName().replace("\"", "") + "\"")
                .body(new InputStreamResource(resource.getInputStream()));
    }
}
