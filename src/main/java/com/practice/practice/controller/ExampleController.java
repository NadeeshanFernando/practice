package com.practice.practice.controller;

import com.practice.practice.model.dto.ExampleDTO;
import com.practice.practice.model.entity.Example;
import com.practice.practice.service.ExampleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/example")
@RequiredArgsConstructor
@Tag(name = "Example Controller", description = "APIs for example")
public class ExampleController {
    private final ExampleService exampleService;

    @GetMapping
    private List<ExampleDTO> getExamples(){
//        "exampleTime": "2025-08-15T21:44:34.126728"
        return exampleService.getExamples();
    }

    @PostMapping
    private ExampleDTO saveExample(@RequestBody final Example example){
        example.setExampleTime(LocalDateTime.now());
        example.setOffsetDateTime(OffsetDateTime.now().toLocalDateTime());
        return exampleService.saveExamples(example);
    }

}
