package com.skillswap.backend.repository;

import com.skillswap.backend.model.StudyMaterial;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface StudyMaterialRepository extends MongoRepository<StudyMaterial, String> {
    List<StudyMaterial> findByReceiverEmailOrderByUploadedAtDesc(String receiverEmail);
    List<StudyMaterial> findBySenderEmailOrderByUploadedAtDesc(String senderEmail);
}
