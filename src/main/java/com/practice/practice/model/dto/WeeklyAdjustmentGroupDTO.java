package com.practice.practice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeeklyAdjustmentGroupDTO {
    private Long employeeId;
    private String sortIdentifier;
    private List<WeeklyAdjustmentAttributeDTO> adjustments;
}
