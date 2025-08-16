package com.practice.practice.service;

import com.practice.practice.repo.EmployeeSkillsRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeSkillsService {
  private final EmployeeSkillsRepo employeeSkillsRepo;

  public List<String> getUniqueSkills() {
    List<String> employeeSkills = employeeSkillsRepo.getAllSkillNamesOrderByDistinction();
    if (employeeSkills == null || employeeSkills.isEmpty()) {
      throw new EntityNotFoundException("No skills found");
    }
    return employeeSkillsRepo.getAllSkillNamesOrderByDistinction();
  }
}
