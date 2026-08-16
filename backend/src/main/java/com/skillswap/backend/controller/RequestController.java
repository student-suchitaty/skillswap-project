package com.skillswap.backend.controller;

import com.skillswap.backend.model.Request;
import com.skillswap.backend.service.RequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")

public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping("/send-request")

    public Request sendRequest(
            @RequestBody Request request){

        return requestService
                .sendRequest(request);

    }

    @GetMapping("/my-requests/{email}")

    public List<Request> getMyRequests(

            @PathVariable String email) {

        return requestService
                .getMyRequests(email);

    }
}