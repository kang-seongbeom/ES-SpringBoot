package com.example.es_springboot.domain.es;

import com.example.es_springboot.controller.dto.port_in.RequestUserSaveDto;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;


@Document(indexName = "user_index")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setting(settingPath = "elastic/es-setting.json")
@Mapping(mappingPath = "elastic/es-mapping.json")
public class UserDocument {

    private Long id;
    private String name;
    private Long age;
    private String korean;
    private String english;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss")
    private LocalDateTime created;

    public static UserDocument of(RequestUserSaveDto dto) {
        return UserDocument.builder()
                .id(dto.getId())
                .name(dto.getName())
                .age(dto.getAge())
                .korean(dto.getKorean())
                .english(dto.getEnglish())
                .created(LocalDateTime.now())
                .build();
    }
}
