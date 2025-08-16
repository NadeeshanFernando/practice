/**
 * Copyright (c) 2025 nadeeshan_fdz. All rights reserved.
 * <p>
 * This file is part of the Spring Boot Project and may not be
 * copied, modified, or distributed without permission.
 * <p>
 * Author: nadeeshan_fdz
 * Date: 07/07/2025
 */

package com.practice.practice.model.entity;

import com.practice.practice.model.enums.AttributeDataType;
import jakarta.persistence.*;
import lombok.*;

/**
 * Author by nadeeshan_fdz, Date = "07/07/2025"
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter @Setter
@Table(name = "calculated_adjustment_attribute")
public class CalculatedAdjustmentAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "employee_swms_id", nullable = false)
    private String employeeSwmsId;

    @Column(name = "sort_identifier", nullable = false, length = 80)
    private String sortIdentifier;

    @Column(name = "attribute_type", nullable = false)
    private String attributeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false)
    private AttributeDataType dataType;

    @Column(name = "value")
    private String value;

    @Column(name = "fiscal_year")
    private int fiscalYear;

    @Column(name = "fiscal_week")
    private int fiscalWeek;

    @Column(name = "calculation_request_id")
    private String calculationRequestId;
}

