/**
 * Copyright (c) 2025 nadeeshan_fdz. All rights reserved.
 * <p>
 * This file is part of the Spring Boot Project and may not be
 * copied, modified, or distributed without permission.
 * <p>
 * Author: nadeeshan_fdz
 * Date: 07/07/2025
 */

package com.practice.practice.service;

import com.practice.practice.model.dto.WeeklyAdjustmentAttributeDTO;
import com.practice.practice.model.dto.WeeklyAdjustmentGroupDTO;
import com.practice.practice.model.entity.CalculatedAdjustmentAttribute;
import com.practice.practice.model.WeeklyAdjustment;
import com.practice.practice.model.enums.AttributeDataType;
import com.practice.practice.repo.CalculatedAdjustmentAttributeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author by nadeeshan_fdz, Date = "07/07/2025"
 */
@Service
public class AdjustmentServiceImpl {
    @Autowired
    CalculatedAdjustmentAttributeRepo repository;

    public void processAdjustments(List<WeeklyAdjustment> adjustments) {
        Map<String, List<WeeklyAdjustment>> grouped =
                adjustments.stream()
                        .filter(adj -> adj.getEmployeeId() != null)
                        .collect(Collectors.groupingBy(WeeklyAdjustment::getEmployeeId));

        List<CalculatedAdjustmentAttribute> calculatedList = new ArrayList<>();

        for (Map.Entry<String, List<WeeklyAdjustment>> entry : grouped.entrySet()) {
            String empId = entry.getKey();
            List<WeeklyAdjustment> adjList = entry.getValue();

            for (WeeklyAdjustment adj : adjList) {
                if (isAllFieldsEmpty(adj)) continue;

                String sortKey = buildSortKey(empId, adj);

                calculatedList.add(buildAttribute("WEEKLY_ADJUSTMENT_JOB_CODE", empId, sortKey, AttributeDataType.STRING, adj.getJobCode()));
                calculatedList.add(buildAttribute("WEEKLY_ADJUSTMENT_PIECES", empId, sortKey, AttributeDataType.NUMERIC, adj.getPieces()));
                calculatedList.add(buildAttribute("WEEKLY_ADJUSTMENT_PALLETS", empId, sortKey, AttributeDataType.NUMERIC, adj.getPallets()));
                calculatedList.add(buildAttribute("WEEKLY_ADJUSTMENT_ERROR_CODE", empId, sortKey, AttributeDataType.STRING, adj.getErrorCode()));
                calculatedList.add(buildAttribute("WEEKLY_ADJUSTMENT_ITEM_ID", empId, sortKey, AttributeDataType.STRING, adj.getErrorItemId()));
                calculatedList.add(buildAttribute("WEEKLY_ADJUSTMENT_ERROR_AMOUNT", empId, sortKey, AttributeDataType.NUMERIC, adj.getErrorAmount()));
            }
        }

        repository.saveAll(calculatedList.stream().filter(Objects::nonNull).toList());
    }

    public List<WeeklyAdjustmentGroupDTO> fetchWeeklyAdjustmentsGrouped() {
        List<CalculatedAdjustmentAttribute> all = repository.findAllByOrderBySortIdentifierAsc();

        return all.stream()
                .collect(Collectors.groupingBy(
                        attr -> attr.getEmployeeId() + "|" + attr.getSortIdentifier(),
                        LinkedHashMap::new,
                        Collectors.mapping(attr -> new WeeklyAdjustmentAttributeDTO(
                                attr.getAttributeType(),
                                attr.getDataType(),
                                attr.getValue()
                        ), Collectors.toList())
                ))
                .entrySet()
                .stream()
                .map(entry -> {
                    String[] keys = entry.getKey().split("\\|", 2);
                    WeeklyAdjustmentGroupDTO group = new WeeklyAdjustmentGroupDTO();
                    group.setEmployeeId(Long.parseLong(keys[0]));
                    group.setSortIdentifier(keys[1]);
                    group.setAdjustments(entry.getValue());
                    return group;
                })
                .collect(Collectors.toList());
    }

    private boolean isAllFieldsEmpty(WeeklyAdjustment adj) {
        return adj.getJobCode() == null &&
                adj.getPieces() == null &&
                adj.getPallets() == null &&
                adj.getErrorCode() == null &&
                adj.getErrorItemId() == null &&
                adj.getErrorAmount() == null;
    }

    private String buildSortKey(String empId, WeeklyAdjustment adj) {
        return String.join("|",
                normalize(empId),
                normalize(adj.getErrorCode()),
                normalize(adj.getErrorItemId()),
                normalize(adj.getJobCode())
        );
    }

    private String normalize(String value) {
        return (value == null || value.isBlank()) ? "~" : value.trim().toUpperCase();
    }

    private CalculatedAdjustmentAttribute buildAttribute(String type, String empId, String sortKey,
                                                         AttributeDataType dataType, Object value) {
        if (value == null) return null;

        CalculatedAdjustmentAttribute attr = new CalculatedAdjustmentAttribute();
        attr.setEmployeeId(Long.parseLong(empId));
        attr.setEmployeeSwmsId(empId);
        attr.setSortIdentifier(sortKey);
        attr.setAttributeType(type);
        attr.setDataType(dataType);
        attr.setValue(value.toString());
        attr.setFiscalYear(2025); // hardcoded for now
        attr.setFiscalWeek(27);
        attr.setCalculationRequestId("REQ-" + empId);

        return attr;
    }
}

