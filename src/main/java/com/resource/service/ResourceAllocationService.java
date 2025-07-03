package com.resource.service;

import com.resource.Repository.MicroserviceRepository;
import com.resource.Repository.ResourceRepository;
import com.resource.algorithm.MOMAAlgorithm;
import com.resource.entity.Microservice;
import com.resource.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceAllocationService {

    @Autowired
    private MicroserviceRepository microserviceRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    public String optimizeResourceAllocation() {
        List<Resource> resources = resourceRepository.findAll();
        List<Microservice> microservices = microserviceRepository.findAll();

        if (resources.isEmpty() || microservices.isEmpty()) {
            return " No resources or microservices available for allocation.";
        }

        MOMAAlgorithm moma = new MOMAAlgorithm(resources, microservices);
        return moma.optimizeResourceAllocation();  // Returns formatted deployment plan
    }
}
