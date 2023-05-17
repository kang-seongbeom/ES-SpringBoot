package com.example.es_springboot.service;

import com.example.es_springboot.controller.dto.out.ResponseUserDto;
import com.example.es_springboot.domain.es.UserDocument;
import com.example.es_springboot.controller.dto.in.RequestUserSaveDto;
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

    public List<ResponseUserDto> findByName(String name){
        List<ResponseUserDto> users = repository.findByName(name)
                .stream()
                .map(ResponseUserDto::of)
                .collect(Collectors.toList());

        return users;
    }

    public void saveAll(List<RequestUserSaveDto> info) {
        List<UserDocument> users = info.stream()
                .map(UserDocument::of)
                .collect(Collectors.toList());

        repository.saveAll(users);
    }
}
