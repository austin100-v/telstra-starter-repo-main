package com.example.telstra.model;

import javax.persistence.*;

@Entity
@Table(name = "sim_activation")
public class SimActivation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String iccid;

    private String customerEmail;

    private boolean active;

    // Constructors, getters, and setters

    public SimActivation() {}

    public SimActivation(String iccid, String customerEmail, boolean active) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
    }

    // Getters and setters
}
