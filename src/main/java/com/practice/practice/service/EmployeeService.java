package com.practice.practice.service;

import com.practice.practice.model.dto.EmployeeDTO;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    EmployeeDTO addEmployee(EmployeeDTO dto);
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployeeById(final UUID id);
    EmployeeDTO updateEmployeeById(final UUID id, final EmployeeDTO employeeDTO);
    void deleteEmployeeById(UUID id);
    List<EmployeeDTO> getEmployeeBySkills(final String skillName);
}
