package com.resource.controller;

import com.resource.service.ResourceAllocationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resource-allocation")
public class ResourceAllocationController {
    private final ResourceAllocationService resourceAllocationService;
    private String lastDeploymentPlan = "";  // Store the last generated plan

    public ResourceAllocationController(ResourceAllocationService resourceAllocationService) {
        this.resourceAllocationService = resourceAllocationService;
    }

    //  POST: Run the MOMA Algorithm and generate a new deployment plan
    @PostMapping("/optimize")
    public String optimize() {
        lastDeploymentPlan = resourceAllocationService.optimizeResourceAllocation();
        return lastDeploymentPlan;
    }

    //  GET: Retrieve the last generated deployment plan
    @GetMapping("/deployment-plan")
    public String getLastDeploymentPlan() {
        if (lastDeploymentPlan.isEmpty()) {
            return "No deployment plan generated yet. Run /api/resource-allocation/optimize first.";
        }
        return lastDeploymentPlan;
    }
}
