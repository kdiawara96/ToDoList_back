package com.codesign.ToDoList.Services;

import org.springframework.http.ResponseEntity;

public interface AuthServices {

    ResponseEntity<Object> jwt(String grantType , String email, String password, boolean ouiRefresh, String refreshToken);
}
