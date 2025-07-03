package com.resource.entity;

import jakarta.persistence.*;

@Entity
public class Microservice {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int cpuRequirement;
    private int memoryRequirement;

    @ManyToOne  // Many Microservices can be assigned to one Resource
    @JoinColumn(name = "resource_id")
    private Resource allocatedResource;

    public void setAllocatedResource(Resource resource) {
        this.allocatedResource = resource;
    }

    public Resource getAllocatedResource() {
        return this.allocatedResource;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getCpuRequirement() {
        return cpuRequirement;
    }

    public void setCpuRequirement(int cpuRequirement) {
        this.cpuRequirement = cpuRequirement;
    }

    public int getMemoryRequirement() {
        return memoryRequirement;
    }

    public void setMemoryRequirement(int memoryRequirement) {
        this.memoryRequirement = memoryRequirement;
    }
}
