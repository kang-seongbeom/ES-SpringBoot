package com.example.es_springboot.controller.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class RequestSearchConditionDto {

    @Nullable
    private Long id;

    @Nullable
    private String name;

    @Nullable
    private Long age;

    @Nullable
    private String korean;

    @Nullable
    private String english;
}
