package com.example.telstra.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SimActivationController {

    @PostMapping("/activate-sim")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String actuatorUrl = "http://localhost:8444/actuate";
        HttpEntity<SimActivationRequest> entity = new HttpEntity<>(new SimActivationRequest(request.getIccid()));
        ResponseEntity<ActuatorResponse> response = restTemplate.postForEntity(actuatorUrl, entity, ActuatorResponse.class);

        if (response.getBody().isSuccess()) {
            return ResponseEntity.ok("Activation successful");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Activation failed");
        }
    }
}
