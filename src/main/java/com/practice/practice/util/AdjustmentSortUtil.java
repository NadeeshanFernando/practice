/**
 * Copyright (c) 2025 nadeeshan_fdz. All rights reserved.
 * <p>
 * This file is part of the Spring Boot Project and may not be
 * copied, modified, or distributed without permission.
 * <p>
 * Author: nadeeshan_fdz
 * Date: 07/07/2025
 */

package com.practice.practice.util;

import com.practice.practice.model.WeeklyAdjustment;

/**
 * Author by nadeeshan_fdz, Date = "07/07/2025"
 */
public class AdjustmentSortUtil {
    public static String buildSortKey(WeeklyAdjustment adj) {
        return String.join("|",
                safe(adj.getEmployeeId(), 10),
                safe(adj.getErrorCode(), 10),
                safe(adj.getErrorItemId(), 20),
                safe(adj.getJobCode(), 10)
        );
    }

    private static String safe(String val, int width) {
        return String.format("%-" + width + "s", val == null ? "~" : val.trim().toUpperCase());
    }
}

