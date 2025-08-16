package com.practice.practice.repo;

import com.practice.practice.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, UUID> {
  @Query("SELECT e FROM Employee e LEFT JOIN e.skills es WHERE es.skillName = :sn")
  List<Employee> findEmployeesBySkill(@Param("sn") String skillName);
}
