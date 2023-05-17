package com.example.es_springboot.controller;

import com.example.es_springboot.controller.dto.in.RequestUserSaveDto;
import com.example.es_springboot.controller.dto.out.ResponseUserDto;
import com.example.es_springboot.domain.es.UserDocument;
import com.example.es_springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/save/users")
    public ResponseEntity<Void> saveAll(@RequestBody Map<String, List<RequestUserSaveDto>> users){
        service.saveAll(users.getOrDefault("users", List.of()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name")
    ResponseEntity<List<ResponseUserDto>> searchByName(@RequestParam String name){
        return ResponseEntity.ok(service.findByName(name));
    }
}