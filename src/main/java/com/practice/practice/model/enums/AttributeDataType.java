/**
 * Copyright (c) 2025 nadeeshan_fdz. All rights reserved.
 * <p>
 * This file is part of the Spring Boot Project and may not be
 * copied, modified, or distributed without permission.
 * <p>
 * Author: nadeeshan_fdz
 * Date: 07/07/2025
 */

package com.practice.practice.model.enums;

import java.math.BigDecimal;

/**
 * Author by nadeeshan_fdz, Date = "07/07/2025"
 */
public enum AttributeDataType {
    STRING,
    NUMERIC,
    BOOLEAN;

    public static AttributeDataType fromValue(Object value) {
        if (value instanceof BigDecimal || value instanceof Integer || value instanceof Double) {
            return NUMERIC;
        } else if (value instanceof Boolean) {
            return BOOLEAN;
        } else {
            return STRING;
        }
    }
}

