package com.resource.controller;
import com.resource.entity.Resource;
import com.resource.Repository.ResourceRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    private final ResourceRepository resourceRepository;

    public ResourceController(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }


    @GetMapping
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }


    @PostMapping
    public Resource addResource(@RequestBody Resource resource) {
        return resourceRepository.save(resource);
    }
}
