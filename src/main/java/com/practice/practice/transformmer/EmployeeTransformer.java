package com.practice.practice.transformmer;

import com.practice.practice.model.dto.EmployeeDTO;
import com.practice.practice.model.dto.EmployeeSkillsDTO;
import com.practice.practice.model.entity.Employee;
import com.practice.practice.model.entity.EmployeeSkills;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class EmployeeTransformer {

  private EmployeeTransformer() {}

  public static EmployeeDTO transformToObject(Employee e) {
    if (e == null) return null;

    List<EmployeeSkillsDTO> skills =
            e.getSkills() == null ? List.of()
                    : e.getSkills().stream()
                    .filter(Objects::nonNull)
                    .map(EmployeeTransformer::toSkillDto)
                    .toList();

    return new EmployeeDTO(
            e.getId(),
            e.getName(),
            e.getDepartment(),
            skills
    );
  }

  public static List<EmployeeDTO> transformToList(List<Employee> employees) {
    if (employees == null || employees.isEmpty()) return List.of();
    return employees.stream()
            .filter(Objects::nonNull)
            .map(EmployeeTransformer::transformToObject)
            .toList();
  }

  private static EmployeeSkillsDTO toSkillDto(EmployeeSkills s) {
    return new EmployeeSkillsDTO(
            s.getId(),
            s.getSkillName(),
            s.getSkillLevel()
    );
  }
}
