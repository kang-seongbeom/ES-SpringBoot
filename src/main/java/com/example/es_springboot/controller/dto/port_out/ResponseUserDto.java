package com.example.es_springboot.controller.dto.port_out;

import com.example.es_springboot.domain.es.UserDocument;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.core.Nullable;
import org.springframework.util.StringUtils;

@Builder
public class ResponseUserDto {

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

    ResponseUserDto(){}

    public static ResponseUserDto of(UserDocument user){
        ResponseUserDto dto = new ResponseUserDto();

        if(user.getId() != null){
            dto.id = user.getId();
        }

        if(user.getAge() != null && user.getAge() >= 0){
            dto.age = user.getAge();
        }

        if(StringUtils.hasText(user.getName())){
            dto.name = user.getName();
        }

        if(StringUtils.hasText(user.getKorean())){
            dto.korean = user.getKorean();
        }

        if(StringUtils.hasText(user.getEnglish())){
            dto.english = user.getEnglish();
        }

        return dto;
    }
}
