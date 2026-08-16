package com.skillswap.backend.service;

import com.skillswap.backend.model.Request;
import com.skillswap.backend.repository.RequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public Request sendRequest(
            Request request){

        request.setStatus("Pending");

        return requestRepository.save(request);

    }

    public List<Request> getMyRequests(String email) {

        return requestRepository
                .findByReceiverEmail(email);

    }


}