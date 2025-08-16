/**
 * Copyright (c) 2025 nadeeshan_fdz. All rights reserved.
 * <p>
 * This file is part of the Spring Boot Project and may not be
 * copied, modified, or distributed without permission.
 * <p>
 * Author: nadeeshan_fdz
 * Date: 07/07/2025
 */

package com.practice.practice.controller;

import com.practice.practice.model.WeeklyAdjustment;
import com.practice.practice.model.dto.WeeklyAdjustmentGroupDTO;
import com.practice.practice.service.AdjustmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author by nadeeshan_fdz, Date = "07/07/2025"
 */
@RestController
@RequestMapping("/adjustments")
public class AdjustmentController {
    @Autowired
    AdjustmentServiceImpl service;

    @PostMapping("/process")
    public ResponseEntity<String> process(@RequestBody List<WeeklyAdjustment> adjustments) {
        service.processAdjustments(adjustments);
        return ResponseEntity.ok("Adjustments processed.");
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<WeeklyAdjustmentGroupDTO>> fetchSortedAdjustments() {
        List<WeeklyAdjustmentGroupDTO> adjustments = service.fetchWeeklyAdjustmentsGrouped();
        return ResponseEntity.ok(adjustments);
    }
}

