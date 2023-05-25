package com.example.es_springboot.repository.es;

import com.example.es_springboot.controller.dto.port_in.RequestSearchConditionDto;
import com.example.es_springboot.domain.es.UserDocument;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Repository
@RequiredArgsConstructor
public class UserSearchNativeQueryRepository {

    private final ElasticsearchOperations operations;

    public List<UserDocument> findByNativeCondition(RequestSearchConditionDto dto) {
        Query query = createConditionNativeQuery(dto);

        SearchHits<UserDocument> search = operations.search(query, UserDocument.class);
        return search
                .stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    private NativeSearchQuery createConditionNativeQuery(RequestSearchConditionDto dto) {
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();

        // BoolQueryBuilder mustBoolQueryBuilder = boolQuery();
        BoolQueryBuilder shouldBoolQueryBuilder = boolQuery();
        BoolQueryBuilder filterBoolQueryBuilder = boolQuery();


        if(dto == null){
            return query.build();
        }

        if(dto.getId() != null){
            filterBoolQueryBuilder.filter(matchQuery("id", dto.getId()));
        }

        if(dto.getAge() != null && dto.getAge() > 0){
            shouldBoolQueryBuilder.should(matchQuery("age", dto.getAge()));
        }

        if(StringUtils.hasText(dto.getName())){
            shouldBoolQueryBuilder.should(matchQuery("name", dto.getName()));
        }

        if(StringUtils.hasText(dto.getKorean())){
            shouldBoolQueryBuilder.should(matchQuery("korean", dto.getKorean()));
        }

        if(StringUtils.hasText(dto.getEnglish())){
            shouldBoolQueryBuilder.should(matchQuery("english", dto.getEnglish()));
        }

        query.withQuery(shouldBoolQueryBuilder)
                .withFilter(filterBoolQueryBuilder)
                .withSorts(SortBuilders.fieldSort("age")
                        .order(SortOrder.DESC));

        return query.build();
    }

}
