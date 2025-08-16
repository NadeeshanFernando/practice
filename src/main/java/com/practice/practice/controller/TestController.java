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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author by nadeeshan_fdz, Date = "07/07/2025"
 */
@RestController
public class TestController {
    @GetMapping
    private String getMsg(){
        return "Application Running...";
    }
}
