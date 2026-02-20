package com.practice.practice.controller;

import com.practice.practice.model.entity.WeeklyCalculationStatus;
import com.practice.practice.service.WeeklyManifestVersioningService;
import com.practice.practice.service.WorkflowDemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weeks")
public class WeeklyDemoController {

    private final WorkflowDemoService workflow;
    private final WeeklyManifestVersioningService versioning;

    public WeeklyDemoController(WorkflowDemoService workflow, WeeklyManifestVersioningService versioning) {
        this.workflow = workflow;
        this.versioning = versioning;
    }

    @PostMapping("/{siteId}/{fy}/{fw}/calculate")
    public ResponseEntity<String> calculate(
            @PathVariable String siteId, @PathVariable int fy, @PathVariable int fw,
            @RequestParam(defaultValue = "demo-user") String user
    ) {
        String requestId = workflow.calculate(siteId, fy, fw, user);
        return ResponseEntity.ok("CALCULATION STARTED. requestId=" + requestId);
    }

    @PostMapping("/calculate/{requestId}/complete")
    public ResponseEntity<String> complete(
            @PathVariable String requestId,
            @RequestParam(defaultValue = "true") boolean success
    ) {
        WeeklyCalculationStatus updated = workflow.completeCalculation(requestId, success);
        return ResponseEntity.ok("CALCULATION " + updated.getStatus() + " for requestId=" + requestId);
    }

    @PostMapping("/{siteId}/{fy}/{fw}/approve")
    public ResponseEntity<String> approve(
            @PathVariable String siteId, @PathVariable int fy, @PathVariable int fw,
            @RequestParam(defaultValue = "approver") String user
    ) {
        WeeklyCalculationStatus s = workflow.approve(siteId, fy, fw, user);
        return ResponseEntity.ok("APPROVED. requestId=" + s.getRequestId());
    }

    @GetMapping("/{siteId}/{fy}/{fw}/download-weekly-manifest")
    public ResponseEntity<String> downloadWeeklyManifest(
            @PathVariable String siteId, @PathVariable int fy, @PathVariable int fw
    ) {
        return ResponseEntity.ok(versioning.downloadWeeklyManifest(siteId, fy, fw));
    }
}