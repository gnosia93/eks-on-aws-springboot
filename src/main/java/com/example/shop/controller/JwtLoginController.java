package com.example.shop.controller;

import com.example.shop.dto.LoginRequest;
import com.example.shop.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/jwt")
public class JwtLoginController {

    private final MemberService memberService;
    private final String jwtSecretKey = "admin22admin";

    @CrossOrigin(origins = "*" , exposedHeaders = "**")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
                                   HttpServletRequest httpServletRequest) {
        log.info("userId: " + loginRequest.getUserId());
        log.info("password: " + loginRequest.getPassword());
        log.info("header:" + httpServletRequest.getHeader("Authorization"));
        log.info("User-Agent:" + httpServletRequest.getHeader("User-Agent"));

        String authToken = "tylhtvGM6Duy8q0ZBbGaTg2FZefLfyeEeMZvCXlU2bEiinnZcLSACTxxxxxxxxxx";

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setBearerAuth(authToken);

        return ResponseEntity.status(HttpStatus.OK)
                .headers(responseHeaders)
                .body("jwt login..");
    }
}
