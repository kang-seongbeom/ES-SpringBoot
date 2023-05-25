package com.example.es_springboot.repository.es;

import com.example.es_springboot.controller.dto.port_in.RequestSearchConditionDto;
import com.example.es_springboot.domain.es.UserDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserSearchCriteriaQueryRepository {

    private final ElasticsearchOperations operations;

    public List<UserDocument> findByCriteriaCondition(RequestSearchConditionDto dto) {
        CriteriaQuery query = createConditionCriteriaQuery(dto);

        SearchHits<UserDocument> search = operations.search(query, UserDocument.class);
        return search
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    private CriteriaQuery createConditionCriteriaQuery(RequestSearchConditionDto dto){
        CriteriaQuery query = new CriteriaQuery(new Criteria());

        if(dto == null){
            return query;
        }

        if(dto.getId() != null){
            query.addCriteria(Criteria.where("id").is(dto.getId()));
        }

        if(dto.getAge() != null && dto.getAge() > 0){
            query.addCriteria(Criteria.where("age").is(dto.getAge()));
        }

        if(StringUtils.hasText(dto.getName())){
            query.addCriteria(Criteria.where("name").is(dto.getName()));
        }

        if(StringUtils.hasText(dto.getKorean())){
            query.addCriteria(Criteria.where("korean").is(dto.getKorean()));
        }

        if(StringUtils.hasText(dto.getEnglish())){
            query.addCriteria(Criteria.where("english").is(dto.getEnglish()));
        }

        return query;
    }
}
