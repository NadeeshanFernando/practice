package com.practice.practice.transformmer;

import com.practice.practice.model.dto.EmployeeDTO;
import com.practice.practice.model.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeTransformer {
  private EmployeeTransformer() {
  }

  public static List<EmployeeDTO> transformToList(List<Employee> employeeList) {
    List<EmployeeDTO> employeeDTOList = new ArrayList<>();
    for (Employee e : employeeList) {
      EmployeeDTO employeeDTO = new EmployeeDTO(e.getId(),e.getName(),e.getDepartment(),e.getCreatedAt(),e.getUpdatedAt(),EmployeeSkillsTransformer.transform(e.getSkills()));
      employeeDTOList.add(employeeDTO);
    }
    return employeeDTOList;
  }

  public static EmployeeDTO transformToObject(Employee e) {
    EmployeeDTO employeeDTO = new EmployeeDTO(e.getId(), e.getName(), e.getDepartment(), e.getCreatedAt(), e.getUpdatedAt(), EmployeeSkillsTransformer.transform(e.getSkills()));
    return employeeDTO;
  }
}
