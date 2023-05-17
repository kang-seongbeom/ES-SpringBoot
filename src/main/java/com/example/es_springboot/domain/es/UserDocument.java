package com.example.es_springboot.domain.es;

import com.example.es_springboot.controller.dto.in.RequestUserSaveDto;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Document(indexName = "user_index")
@Entity
@Mapping(mappingPath = "elsatic/es-mapping.json")
@Setting(settingPath = "elsatic/es-setting.json")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class UserDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long age;
    private String description;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss")
    private LocalDateTime created;

    public static UserDocument of(RequestUserSaveDto dto){
        return UserDocument.builder()
                .id(dto.getId())
                .name(dto.getName())
                .age(dto.getAge())
                .description(dto.getDescription())
                .created(LocalDateTime.now())
                .build();
    }
}
