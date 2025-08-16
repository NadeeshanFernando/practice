package com.practice.practice.service;

import com.practice.practice.model.dto.ExampleDTO;
import com.practice.practice.model.entity.Example;
import com.practice.practice.repo.ExampleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.practice.practice.transformer.ExampleTransformer.toExampleDto;
import static com.practice.practice.transformer.ExampleTransformer.toExampleDtoList;

@Service
@RequiredArgsConstructor
public class ExampleService {
    private final ExampleRepo exampleRepo;

    public List<ExampleDTO> getExamples(){
        return toExampleDtoList(exampleRepo.findAll());
    }

    public ExampleDTO saveExamples(final Example example){
        return toExampleDto(exampleRepo.save(example));
    }
}
