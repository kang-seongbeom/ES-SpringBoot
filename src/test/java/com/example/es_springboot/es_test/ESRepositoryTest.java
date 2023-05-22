package com.example.es_springboot.es_test;

import com.example.es_springboot.config.ElasticTest;
import com.example.es_springboot.controller.dto.port_in.RequestUserSaveDto;
import com.example.es_springboot.domain.es.UserDocument;
import com.example.es_springboot.repository.es.UserSearchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(ElasticTest.class)
@SpringBootTest(classes = UserSearchRepository.class)
public class ESRepositoryTest {

    @Autowired
    UserSearchRepository repository;

    @Test
    void test() {
        // given
        RequestUserSaveDto info = RequestUserSaveDto
                .builder()
                .id(1L)
                .name("ksb")
                .age(20L)
                .korean("하이 케이 에스 비")
                .english("hello ksb")
                .build();

        UserDocument user = UserDocument.of(info);

        // when
        repository.save(user);

        //then
        UserDocument result = repository.findByName("ksb").get(0);

        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getAge()).isEqualTo(user.getAge());
        assertThat(result.getKorean()).isEqualTo(user.getKorean());
        assertThat(result.getEnglish()).isEqualTo(user.getEnglish());
    }
}
