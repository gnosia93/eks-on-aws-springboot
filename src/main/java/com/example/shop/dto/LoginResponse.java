package com.example.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {

    String sessionId;
    String emailAddress;
    String loginDate;
    boolean isLogined;

    @Builder
    public LoginResponse(String sessionId, String emailAddress, String loginDate, boolean isLogined) {
        this.sessionId = sessionId;
        this.emailAddress = emailAddress;
        this.loginDate = loginDate;
        this.isLogined = isLogined;
    }
}
