package com.practice.practice.transformer;

import com.practice.practice.model.dto.ExampleDTO;
import com.practice.practice.model.entity.Example;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExampleTransformer {

    public static ExampleDTO toExampleDto(final Example example){
        ExampleDTO exampleDTO = new ExampleDTO();
        exampleDTO.setId(example.getId());
        exampleDTO.setExampleName(example.getExampleName());
        exampleDTO.setExampleTime(example.getExampleTime());
        exampleDTO.setOffsetDateTime(OffsetDateTime.from(example.getOffsetDateTime()));
        return exampleDTO;
    }

    public static List<ExampleDTO> toExampleDtoList(final List<Example> exampleList){
        List<ExampleDTO> exampleDTOList = new ArrayList<>();
        for (Example example : exampleList){
            ExampleDTO exampleDTO = new ExampleDTO();
            exampleDTO.setId(example.getId());
            exampleDTO.setExampleName(example.getExampleName());
            exampleDTO.setExampleTime(example.getExampleTime());
            exampleDTO.setOffsetDateTime(OffsetDateTime.from(example.getOffsetDateTime()));
            exampleDTOList.add(exampleDTO);
        }
        return exampleDTOList;
    }
}
