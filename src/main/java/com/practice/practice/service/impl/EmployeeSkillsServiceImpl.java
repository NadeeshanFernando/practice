package com.practice.practice.service.impl;

import com.practice.practice.repo.EmployeeSkillsRepository;
import com.practice.practice.service.EmployeeSkillsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeSkillsServiceImpl implements EmployeeSkillsService {
  private final EmployeeSkillsRepository employeeSkillsRepository;

  @Override
  public List<String> getUniqueSkills() {
    List<String> employeeSkills = employeeSkillsRepository.getAllSkillNamesOrderByDistinction();
    if (employeeSkills == null || employeeSkills.isEmpty()) {
      throw new EntityNotFoundException("No skills found");
    }
    return employeeSkillsRepository.getAllSkillNamesOrderByDistinction();
  }
}
