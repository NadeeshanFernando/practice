package com.practice.practice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "example")
public class Example {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "example_name", nullable = false)
    private String exampleName;

    @Column(name = "example_time", nullable = false)
    private LocalDateTime exampleTime;

    @Column(name = "offset_date_time", nullable = false)
    private LocalDateTime offsetDateTime;
}
