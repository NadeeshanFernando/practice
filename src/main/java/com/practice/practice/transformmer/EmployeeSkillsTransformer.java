package com.practice.practice.transformmer;

import com.practice.practice.model.dto.EmployeeSkillsDTO;
import com.practice.practice.model.entity.EmployeeSkills;
import com.practice.practice.model.enums.SkillLevel;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSkillsTransformer {
  private EmployeeSkillsTransformer() {
  }

  public static List<EmployeeSkillsDTO> transform(List<EmployeeSkills> employeeSkills) {
    List<EmployeeSkillsDTO> employeeSkillsDTOList = new ArrayList<>();
    for (EmployeeSkills es : employeeSkills) {
      EmployeeSkillsDTO employeeSkillDTO = new EmployeeSkillsDTO(es.getId(),es.getSkillName(), SkillLevel.getLevel(es.getSkillLevel()).getLevelLabel());
      employeeSkillsDTOList.add(employeeSkillDTO);
    }
    return employeeSkillsDTOList;
  }
}
