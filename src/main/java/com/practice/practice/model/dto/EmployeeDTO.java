package com.practice.practice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
  private UUID employeeId;
  private String name;
  private String department;
  private List<EmployeeSkillsDTO> skills;

  public EmployeeDTO(UUID employeeId, String name, String department) {
    this.employeeId = employeeId;
    this.name = name;
    this.department = department;
    this.skills = skills;
  }
}
