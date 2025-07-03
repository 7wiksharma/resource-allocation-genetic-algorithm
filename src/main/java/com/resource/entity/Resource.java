package com.resource.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nodeName;
    private int availableCpu;
    private int availableMemory;
    @OneToMany(mappedBy = "allocatedResource", cascade = CascadeType.ALL)
    private List<Microservice> microservices;
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getAvailableCpu() {
        return availableCpu;
    }

    public void setAvailableCpu(int availableCpu) {
        this.availableCpu = availableCpu;
    }

    public int getAvailableMemory() {
        return availableMemory;
    }

    public void setAvailableMemory(int availableMemory) {
        this.availableMemory = availableMemory;
    }

    }
