package com.codesign.ToDoList.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesign.ToDoList.Services.AuthServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor

public class authControllers {
    
    private final AuthServices serives;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(String grantType, String email, String password, boolean ouiRefresh, String refreshToken){
        return serives.jwt(grantType, email, password, ouiRefresh, refreshToken);
    }

    @GetMapping("/test")
    public String getMethodName() {
        return "test";
    }
    
}
