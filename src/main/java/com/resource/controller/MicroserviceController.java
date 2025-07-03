package com.resource.controller;
import com.resource.Repository.ResourceRepository;
import com.resource.entity.Microservice;
import com.resource.Repository.MicroserviceRepository;
import com.resource.entity.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/microservices")
public class MicroserviceController {
    private final MicroserviceRepository microserviceRepository;
    private final ResourceRepository resourceRepository;
    public MicroserviceController(MicroserviceRepository microserviceRepository , ResourceRepository resourceRepository) {
        this.microserviceRepository = microserviceRepository;
        this.resourceRepository = resourceRepository;
    }

    //  Get All Microservices
    @GetMapping
    public List<Microservice> getAllMicroservices() {
        return microserviceRepository.findAll();
    }

    //  Add a New Microservice
    @PostMapping
    public Microservice addMicroservice(@RequestBody Microservice microservice, @RequestParam(required = false) Long resourceId) {
        if (resourceId != null) {
            Optional<Resource> resource = resourceRepository.findById(resourceId);
            resource.ifPresent(microservice::setAllocatedResource);
        }
        return microserviceRepository.save(microservice);
    }
}
