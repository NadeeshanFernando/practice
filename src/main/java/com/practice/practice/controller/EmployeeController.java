package com.practice.practice.controller;

import com.practice.practice.model.dto.EmployeeDTO;
import com.practice.practice.service.EmployeeService;
import com.practice.practice.service.impl.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody final EmployeeDTO employeeDTO) {
    EmployeeDTO created = employeeService.addEmployee(employeeDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(created); // 201
  }

  @GetMapping
  public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
    return ResponseEntity.ok(employeeService.getAllEmployees()); // 200 + []
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable final UUID id) {
    return ResponseEntity.ok(employeeService.getEmployeeById(id)); // 200 (or 404 via handler)
  }

  @PutMapping("/{id}")
  public ResponseEntity<EmployeeDTO> updateEmployeeById(
          @PathVariable final UUID id,
          @RequestBody final EmployeeDTO employeeDTO
  ) {
    return ResponseEntity.ok(employeeService.updateEmployeeById(id, employeeDTO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployeeById(@PathVariable final UUID id) {
    employeeService.deleteEmployeeById(id);
    return ResponseEntity.noContent().build(); // 204
  }

  @GetMapping("/search")
  public ResponseEntity<List<EmployeeDTO>> getEmployeeBySkills(@RequestParam final String skillName) {
    return ResponseEntity.ok(employeeService.getEmployeeBySkills(skillName));
  }
}
