package com.example.es_springboot.repository.es;

import com.example.es_springboot.domain.es.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

public interface UserSearchRepository extends ElasticsearchRepository<UserDocument, Long> {
}

