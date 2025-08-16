package com.practice.practice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class ExampleDTO {
    private Long id;
    private String exampleName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    private LocalDateTime exampleTime;

    private OffsetDateTime offsetDateTime;
}
