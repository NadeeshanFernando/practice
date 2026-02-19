package com.practice.practice.service;

import com.practice.practice.model.dto.EmployeeDTO;
import com.practice.practice.model.dto.EmployeeSkillsDTO;
import com.practice.practice.model.entity.Employee;
import com.practice.practice.model.entity.EmployeeSkills;
import com.practice.practice.model.enums.SkillLevel;
import com.practice.practice.repo.EmployeeRepo;
import com.practice.practice.repo.EmployeeSkillsRepo;
import com.practice.practice.transformmer.EmployeeTransformer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {
  private final EmployeeRepo employeeRepo;
  private final EmployeeSkillsRepo employeeSkillsRepo;

  public EmployeeDTO addEmployee(final EmployeeDTO employeeDTO) {
    Employee employee = new Employee();
    employee.setId(null);
    employee.setName(employeeDTO.getName());
    employee.setDepartment(employeeDTO.getDepartment());
    employeeRepo.save(employee);

    for (EmployeeSkillsDTO e : employeeDTO.getSkills()){
      EmployeeSkills employeeSkills = new EmployeeSkills();
      employeeSkills.setSkillName(e.getSkillName());
      employeeSkills.setSkillLevel(SkillLevel.getLevel(e.getSkillLevel()).name());
      employeeSkills.setEmployee(employee);
      employeeSkillsRepo.save(employeeSkills);
    }
    return employeeDTO;
  }

  public List<EmployeeDTO> getAllEmployees() {
    return EmployeeTransformer.transformToList(employeeRepo.findAll());
  }

  public EmployeeDTO getEmployeeById(final UUID id) {
    return EmployeeTransformer.transformToObject(employeeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found")));
  }

  public EmployeeDTO updateEmployeeById(final UUID id, final EmployeeDTO employeeDTO) {
    Employee oldEmployee = employeeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    oldEmployee.setName(employeeDTO.getName());
    oldEmployee.setDepartment(employeeDTO.getDepartment());
    employeeRepo.save(oldEmployee);
    return employeeDTO;
  }

  public void deleteEmployeeById(final UUID id) {
    Employee employee = employeeRepo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

    List<EmployeeSkills> skills = employee.getSkills();
    if (skills == null || skills.isEmpty()) {
      throw new EntityNotFoundException("No skills found for employee with ID: " + id);
    }
    employeeSkillsRepo.deleteAll(skills);
    employeeRepo.delete(employee);
  }

  public List<EmployeeDTO> getEmployeeBySkills(final String skillLevel) {
    return EmployeeTransformer.transformToList(employeeRepo.findEmployeesBySkill(skillLevel));
  }
}
