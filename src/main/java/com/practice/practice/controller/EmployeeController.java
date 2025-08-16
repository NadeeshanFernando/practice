package com.practice.practice.controller;

import com.practice.practice.model.dto.EmployeeDTO;
import com.practice.practice.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
@Tag(name = "Employee Controller", description = "APIs for employee")
public class EmployeeController {
  private final EmployeeService employeeService;

  @PostMapping
  public EmployeeDTO addEmployee(@RequestBody final EmployeeDTO employeeDTO) {
    return employeeService.addEmployee(employeeDTO);
  }

  @GetMapping
  public List<EmployeeDTO> getAllEmployees() {
    return employeeService.getAllEmployees();
  }

  @GetMapping("/{id}")
  public EmployeeDTO getEmployeeById(@PathVariable final UUID id) {
    return employeeService.getEmployeeById(id);
  }

  @PutMapping("/{id}")
  public EmployeeDTO updateEmployeeById(@PathVariable final UUID id, @RequestBody final EmployeeDTO employeeDTO) {
    return employeeService.updateEmployeeById(id, employeeDTO);
  }

  @DeleteMapping("/{id}")
  public void deleteEmployeeById(@PathVariable final UUID id) {
    employeeService.deleteEmployeeById(id);
  }

  @GetMapping("/search/{skillName}")
  public List<EmployeeDTO> getEmployeeBySkills(@PathVariable final String skillName) {
    return employeeService.getEmployeeBySkills(skillName);
  }
}
