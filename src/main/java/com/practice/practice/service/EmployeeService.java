package com.practice.practice.service;

import com.practice.practice.exception.ResourceNotFoundException;
import com.practice.practice.model.dto.EmployeeDTO;
import com.practice.practice.model.dto.EmployeeSkillsDTO;
import com.practice.practice.model.entity.Employee;
import com.practice.practice.model.entity.EmployeeSkills;
import com.practice.practice.model.enums.SkillLevel;
import com.practice.practice.repo.EmployeeRepo;
import com.practice.practice.repo.EmployeeSkillsRepo;
import com.practice.practice.transformmer.EmployeeTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final EmployeeRepo employeeRepo;
  private final EmployeeSkillsRepo employeeSkillsRepo;

  @Transactional
  public EmployeeDTO addEmployee(EmployeeDTO dto) {
    Employee emp = new Employee();
    emp.setName(dto.getName());
    emp.setDepartment(dto.getDepartment());

    if (dto.getSkills() != null) {
      emp.setSkills(new ArrayList<>());
      for (var s : dto.getSkills()) {
        EmployeeSkills skill = new EmployeeSkills();
        skill.setSkillName(s.getSkillName());
        skill.setSkillLevel(SkillLevel.getLevel(s.getSkillLevel()).name());
        emp.addSkill(skill); // sets employee both sides
      }
    }

    Employee saved = employeeRepo.save(emp);
    return EmployeeTransformer.transformToObject(saved);
  }

  public List<EmployeeDTO> getAllEmployees() {
    // best practice: return [] when empty
    return EmployeeTransformer.transformToList(employeeRepo.findAll());
  }

  public EmployeeDTO getEmployeeById(final UUID id) {
    Employee employee = employeeRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    return EmployeeTransformer.transformToObject(employee);
  }

  @Transactional
  public EmployeeDTO updateEmployeeById(final UUID id, final EmployeeDTO employeeDTO) {
    Employee employee = employeeRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

    employee.setName(employeeDTO.getName());
    employee.setDepartment(employeeDTO.getDepartment());

    Employee updated = employeeRepo.save(employee);
    return EmployeeTransformer.transformToObject(updated);
  }

  @Transactional
  public void deleteEmployeeById(UUID id) {
    Employee emp = employeeRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    employeeRepo.delete(emp); // skills deleted automatically
  }

  public List<EmployeeDTO> getEmployeeBySkills(final String skillName) {
    return EmployeeTransformer.transformToList(employeeRepo.findEmployeesBySkillFetchSkills(skillName.toUpperCase()));
  }
}
