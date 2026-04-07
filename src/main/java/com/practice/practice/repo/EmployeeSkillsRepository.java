package com.practice.practice.repo;

import com.practice.practice.model.entity.EmployeeSkills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeSkillsRepository extends JpaRepository<EmployeeSkills, UUID> {
  @Query("SELECT DISTINCT es.skillName FROM EmployeeSkills es ORDER BY es.skillName ASC")
  List<String> getAllSkillNamesOrderByDistinction();
}
