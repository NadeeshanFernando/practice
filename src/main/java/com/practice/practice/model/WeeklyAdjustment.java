/**
 * Copyright (c) 2025 nadeeshan_fdz. All rights reserved.
 * <p>
 * This file is part of the Spring Boot Project and may not be
 * copied, modified, or distributed without permission.
 * <p>
 * Author: nadeeshan_fdz
 * Date: 07/07/2025
 */

package com.practice.practice.model;

import lombok.*;

import java.math.BigDecimal;

/**
 * Author by nadeeshan_fdz, Date = "07/07/2025"
 */
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyAdjustment {
    private String employeeId;

    private String jobCode;

    private BigDecimal pieces;

    private BigDecimal pallets;

    private String errorCode;

    private String errorItemId;

    private BigDecimal errorAmount;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public BigDecimal getPieces() {
        return pieces;
    }

    public void setPieces(BigDecimal pieces) {
        this.pieces = pieces;
    }

    public BigDecimal getPallets() {
        return pallets;
    }

    public void setPallets(BigDecimal pallets) {
        this.pallets = pallets;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorItemId() {
        return errorItemId;
    }

    public void setErrorItemId(String errorItemId) {
        this.errorItemId = errorItemId;
    }

    public BigDecimal getErrorAmount() {
        return errorAmount;
    }

    public void setErrorAmount(BigDecimal errorAmount) {
        this.errorAmount = errorAmount;
    }
}

