package com.example.es_springboot.service;

import com.example.es_springboot.controller.dto.port_in.RequestSearchConditionDto;
import com.example.es_springboot.controller.dto.port_out.ResponseUserDto;
import com.example.es_springboot.domain.es.UserDocument;
import com.example.es_springboot.controller.dto.port_in.RequestUserSaveDto;
import com.example.es_springboot.repository.es.UserSearchQueryRepository;
import com.example.es_springboot.repository.es.UserSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserSearchRepository repository;
    private final UserSearchQueryRepository queryRepository;

    public void saveAll(List<RequestUserSaveDto> info) {
        List<UserDocument> users = info.stream()
                .map(UserDocument::of)
                .collect(Collectors.toList());

        repository.saveAll(users);
    }

    public List<ResponseUserDto> findByName(String name) {
        return transInfo(repository.findByName(name));
    }

    public List<ResponseUserDto> findByKorean(String korean) {
        return transInfo(repository.findByKorean(korean));
    }

    public List<ResponseUserDto> findByEnglish(String english) {
        return transInfo(repository.findByEnglish(english));
    }

    public List<ResponseUserDto> findByCondition(RequestSearchConditionDto dto){
        return transInfo(queryRepository.findByCondition(dto));
    }

    private List<ResponseUserDto> transInfo(List<UserDocument> users) {
        return users.stream()
                .map(ResponseUserDto::of)
                .collect(Collectors.toList());
    }

}
