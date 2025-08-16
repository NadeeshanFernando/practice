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
  @JsonFormat(pattern = "MMM dd, yyyy HH:mm", timezone = "Asia/Colombo")
  private LocalDateTime created_at;
  @JsonFormat(pattern = "MMM dd, yyyy HH:mm", timezone = "Asia/Colombo")
  private LocalDateTime updated_at;
  private List<EmployeeSkillsDTO> skills;

  public EmployeeDTO(UUID employeeId, String name, String department, LocalDateTime created_at, LocalDateTime updated_at) {
    this.employeeId = employeeId;
    this.name = name;
    this.department = department;
    this.created_at = created_at;
    this.updated_at = updated_at;
    this.skills = skills;
  }
}
