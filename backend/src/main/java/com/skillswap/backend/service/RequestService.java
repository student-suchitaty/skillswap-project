package com.skillswap.backend.service;

import com.skillswap.backend.model.Request;
import com.skillswap.backend.model.User;
import com.skillswap.backend.repository.RequestRepository;
import com.skillswap.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    // Send Request with Duplicate Check
    public Request sendRequest(Request request) {

        if (request.getSenderEmail() == null ||
                request.getReceiverEmail() == null ||
                request.getSubject() == null ||
                request.getSubject().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Sender, receiver and subject are required."
            );
        }

        String sender = request.getSenderEmail().trim();
        String receiver = request.getReceiverEmail().trim();
        String subject = request.getSubject().trim();

        if (sender.equalsIgnoreCase(receiver)) {
            throw new IllegalArgumentException(
                    "You cannot send a request to yourself."
            );
        }

        // Block duplicate Pending request for the same subject
        if (requestRepository
                .existsBySenderEmailAndReceiverEmailAndSubjectIgnoreCaseAndStatus(
                        sender, receiver, subject, "Pending")) {
            throw new IllegalArgumentException(
                    "You already have a pending request for this subject."
            );
        }

        // Block another request for a subject already accepted
        if (requestRepository
                .existsBySenderEmailAndReceiverEmailAndSubjectIgnoreCaseAndStatusIgnoreCase(
                        sender, receiver, subject, "Accepted")) {
            throw new IllegalArgumentException(
                    "You are already connected for this subject."
            );
        }

        request.setSenderEmail(sender);
        request.setReceiverEmail(receiver);
        request.setSubject(subject);
        request.setStatus("Pending");

        return requestRepository.save(request);
    }

    // Get Sent and Received Requests
    public List<Request> getMyRequests(String email) {
        return requestRepository.findByReceiverEmailOrSenderEmail(
                email, email
        );
    }

    // Accept or Reject Request
    public Request updateStatus(
            String id, String status, String receiverEmail) {

        if (!status.equals("Accepted") &&
                !status.equals("Rejected")) {
            throw new IllegalArgumentException(
                    "Status must be Accepted or Rejected."
            );
        }

        Request request = requestRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Request not found.")
                );

        if (!request.getReceiverEmail()
                .equalsIgnoreCase(receiverEmail)) {
            throw new IllegalArgumentException(
                    "Only the receiver can update this request."
            );
        }

        if (!request.getStatus().equals("Pending")) {
            throw new IllegalArgumentException(
                    "This request has already been processed."
            );
        }

        request.setStatus(status);
        return requestRepository.save(request);
    }

    // Get Accepted Connections
    public List<User> getConnections(String email) {

        List<Request> requests =
                requestRepository.findByReceiverEmailOrSenderEmail(
                        email, email
                );

        List<String> emails = requests.stream()
                .filter(r -> "Accepted".equals(r.getStatus()))
                .map(r -> r.getSenderEmail()
                        .equalsIgnoreCase(email)
                        ? r.getReceiverEmail()
                        : r.getSenderEmail())
                .distinct()
                .toList();

        return userRepository.findByEmailIn(emails);
    }

    public boolean hasAcceptedConnection(
            String email1, String email2) {

        return requestRepository
                .existsBySenderEmailAndReceiverEmailAndStatus(
                        email1, email2, "Accepted")
                || requestRepository
                .existsBySenderEmailAndReceiverEmailAndStatus(
                        email2, email1, "Accepted");
    }
}

