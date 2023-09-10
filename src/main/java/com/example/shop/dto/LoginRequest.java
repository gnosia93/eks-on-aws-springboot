package com.example.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    int userId;
    String password;

    @Builder
    public LoginRequest(int userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
