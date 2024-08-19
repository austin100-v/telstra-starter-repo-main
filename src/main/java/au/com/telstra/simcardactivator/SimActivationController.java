package com.example.telstra.controller;

import com.example.telstra.model.SimActivation;
import com.example.telstra.repository.SimActivationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

@RestController
public class SimActivationController {

    @Autowired
    private SimActivationRepository repository;

    @PostMapping("/activate-sim")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String actuatorUrl = "http://localhost:8444/actuate";
        HttpEntity<SimActivationRequest> entity = new HttpEntity<>(new SimActivationRequest(request.getIccid()));
        ResponseEntity<ActuatorResponse> response = restTemplate.postForEntity(actuatorUrl, entity, ActuatorResponse.class);

        boolean isActive = response.getBody().isSuccess();
        SimActivation activation = new SimActivation(request.getIccid(), request.getCustomerEmail(), isActive);
        repository.save(activation);

        if (isActive) {
            return ResponseEntity.ok("Activation successful");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Activation failed");
        }
    }

    @GetMapping("/get-sim-activation")
    public ResponseEntity<SimActivation> getSimActivation(@RequestParam Long simCardId) {
        SimActivation activation = repository.findById(simCardId).orElse(null);
        if (activation != null) {
            return ResponseEntity.ok(activation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
