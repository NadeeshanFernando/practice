package com.practice.practice.service.impl;

import com.practice.practice.exception.ResourceNotFoundException;
import com.practice.practice.model.dto.EmployeeDTO;
import com.practice.practice.model.entity.Employee;
import com.practice.practice.model.entity.EmployeeSkills;
import com.practice.practice.model.enums.SkillLevel;
import com.practice.practice.repo.EmployeeRepository;
import com.practice.practice.repo.EmployeeSkillsRepository;
import com.practice.practice.service.EmployeeService;
import com.practice.practice.transformmer.EmployeeTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeSkillsRepository employeeSkillsRepository;

  @Override
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

    Employee saved = employeeRepository.save(emp);
    return EmployeeTransformer.transformToObject(saved);
  }

  @Override
  public List<EmployeeDTO> getAllEmployees() {
    // best practice: return [] when empty
    return EmployeeTransformer.transformToList(employeeRepository.findAll());
  }

  @Override
  public EmployeeDTO getEmployeeById(final UUID id) {
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    return EmployeeTransformer.transformToObject(employee);
  }

  @Override
  @Transactional
  public EmployeeDTO updateEmployeeById(final UUID id, final EmployeeDTO employeeDTO) {
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

    employee.setName(employeeDTO.getName());
    employee.setDepartment(employeeDTO.getDepartment());

    Employee updated = employeeRepository.save(employee);
    return EmployeeTransformer.transformToObject(updated);
  }

  @Override
  @Transactional
  public void deleteEmployeeById(UUID id) {
    Employee emp = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    employeeRepository.delete(emp); // skills deleted automatically
  }

  @Override
  public List<EmployeeDTO> getEmployeeBySkills(final String skillName) {
    return EmployeeTransformer.transformToList(employeeRepository.findEmployeesBySkillFetchSkills(skillName.toUpperCase()));
  }
}
