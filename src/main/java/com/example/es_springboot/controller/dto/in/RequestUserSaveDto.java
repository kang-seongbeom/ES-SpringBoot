package com.example.es_springboot.controller.dto.in;

import lombok.*;


@AllArgsConstructor
@Getter
public class RequestUserSaveDto {

    private final Long id;
    private final String name;
    private final Long age;
    private final String description;
}
