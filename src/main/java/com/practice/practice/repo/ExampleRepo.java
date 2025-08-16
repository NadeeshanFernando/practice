package com.practice.practice.repo;

import com.practice.practice.model.entity.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepo extends JpaRepository<Example, Long> {
}
