package com.example.shop.controller;

import com.example.shop.dto.LoginRequest;
import com.example.shop.dto.LoginResponse;
import com.example.shop.dto.MemberDto;
import com.example.shop.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/session")
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest,
                               HttpServletRequest httpServletRequest) {
        log.info("userId: " + loginRequest.getUserId());
        log.info("password: " + loginRequest.getPassword());

        // userId, password 로 로그인 한다.
        // 성공하는 경우 Redis K/V 설정
        // LoginResponse 객체 반환(userId, email, 로그인 성공여부, 로그인일시)
        MemberDto memberDto = memberService.findMember(loginRequest.getUserId());
        LoginResponse.LoginResponseBuilder loginResponse = LoginResponse.builder();
        loginResponse.isLogined(false);

        if(memberDto != null && memberDto.getPassword() != null
                && memberDto.getPassword().equals(loginRequest.getPassword())) {

            HttpSession prevSession = httpServletRequest.getSession();
            prevSession.invalidate();
            log.info("prev session id: " + prevSession.getId());

            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute("emailAddress", memberDto.getEmailAddress());
            session.setMaxInactiveInterval(1800); // Session이 30분동안 유지
            log.info("session id: " + session.getId());

            SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            loginResponse.sessionId(session.getId())
                    .loginDate(dtf.format(now.toDate()))
                    .emailAddress(memberDto.getEmailAddress())
                    .isLogined(true);
        }

        return loginResponse.build();
    }

    @GetMapping("/logout")
    public LoginResponse logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);  // Session이 없으면 null return
        if(session != null) {
            session.invalidate();
        }

        return LoginResponse.builder()
                .isLogined(false)
                .build();
    }

    @GetMapping("/info")
    public LoginResponse info(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);  // Session이 없으면 null return
        String sessionId = session.getId();

        // redis 에서 조회해서 return 한다.
        // session 이 null 인 경우도 체크한다.
        return null;
    }

}
