package com.skillswap.backend.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.skillswap.backend.model.StudyMaterial;
import com.skillswap.backend.repository.StudyMaterialRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudyMaterialService {
    @Autowired private StudyMaterialRepository repository;
    @Autowired private GridFsTemplate gridFsTemplate;
    @Autowired private RequestService requestService;

    public StudyMaterial send(String sender, String receiver, String title,
                              String description, MultipartFile file) throws IOException {
        if (!requestService.hasAcceptedConnection(sender, receiver)) {
            throw new IllegalArgumentException("Study material can be sent only after the request is accepted.");
        }
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("Please select a file.");

        ObjectId fileId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
        StudyMaterial material = new StudyMaterial();
        material.setSenderEmail(sender);
        material.setReceiverEmail(receiver);
        material.setTitle(title);
        material.setDescription(description);
        material.setFileName(file.getOriginalFilename());
        material.setContentType(file.getContentType());
        material.setFileId(fileId.toHexString());
        material.setUploadedAt(LocalDateTime.now());
        return repository.save(material);
    }

    public List<StudyMaterial> received(String email) { return repository.findByReceiverEmailOrderByUploadedAtDesc(email); }
    public List<StudyMaterial> sent(String email) { return repository.findBySenderEmailOrderByUploadedAtDesc(email); }

    public StudyMaterial find(String id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Material not found."));
    }

    public void delete(String id) {
        StudyMaterial material = find(id);
        gridFsTemplate.delete(org.springframework.data.mongodb.core.query.Query.query(
                org.springframework.data.mongodb.core.query.Criteria.where("_id").is(new ObjectId(material.getFileId()))));
        repository.deleteById(id);
    }
}
