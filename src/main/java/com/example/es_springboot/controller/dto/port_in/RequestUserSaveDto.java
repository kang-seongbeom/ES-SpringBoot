package com.example.es_springboot.controller.dto.port_in;


import lombok.*;


@AllArgsConstructor
@Getter
@Builder
public class RequestUserSaveDto {

    private final Long id;
    private final String name;
    private final Long age;
    private final String korean;
    private final String english;
}
