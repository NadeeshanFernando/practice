package com.practice.practice.model.dto;

import com.practice.practice.model.enums.AttributeDataType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class WeeklyAdjustmentAttributeDTO {
    private String attributeType;
    private AttributeDataType dataType;
    private String value;
}
