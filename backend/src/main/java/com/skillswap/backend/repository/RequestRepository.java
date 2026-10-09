package com.skillswap.backend.repository;

import com.skillswap.backend.model.Request;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RequestRepository extends MongoRepository<Request, String> {
    List<Request> findByReceiverEmail(String receiverEmail);
    List<Request> findBySenderEmail(String senderEmail);
    List<Request> findByReceiverEmailOrSenderEmail(String receiverEmail, String senderEmail);
    boolean existsBySenderEmailAndReceiverEmailAndStatus(String senderEmail, String receiverEmail, String status);
    boolean existsBySenderEmailAndReceiverEmail(String senderEmail, String receiverEmail);

    boolean existsBySenderEmailAndReceiverEmailAndSubjectIgnoreCaseAndStatus(String senderEmail, String receiverEmail, String subject, String status);

    boolean existsBySenderEmailAndReceiverEmailAndSubjectIgnoreCase(String senderEmail, String receiverEmail,String subject);
    boolean existsBySenderEmailAndReceiverEmailAndSubjectIgnoreCaseAndStatusIgnoreCase(String senderEmail, String receiverEmail, String subject, String status);


}
