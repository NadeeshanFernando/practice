/**
 * Copyright (c) 2025 nadeeshan_fdz. All rights reserved.
 * <p>
 * This file is part of the Spring Boot Project and may not be
 * copied, modified, or distributed without permission.
 * <p>
 * Author: nadeeshan_fdz
 * Date: 07/07/2025
 */

package com.practice.practice.repo;

import com.practice.practice.model.entity.CalculatedAdjustmentAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author by nadeeshan_fdz, Date = "07/07/2025"
 */
@Repository
public interface CalculatedAdjustmentAttributeRepository extends JpaRepository<CalculatedAdjustmentAttribute, Long> {
    List<CalculatedAdjustmentAttribute> findAllByOrderBySortIdentifierAsc();
}
