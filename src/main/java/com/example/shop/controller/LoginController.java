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
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/session")
public class LoginController {

    private final MemberService memberService;
    private final RedissonClient redissonClient;

    private final String cacheName = "spring:session:sessions:";
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
                               HttpServletRequest httpServletRequest) {
        log.info("userId: " + loginRequest.getUserId());
        log.info("password: " + loginRequest.getPassword());

        // userId, password 로 로그인 한다.
        // 성공하는 경우 Redis K/V 설정
        // LoginResponse 객체 반환(userId, email, 로그인 성공여부, 로그인일시)
        MemberDto memberDto = memberService.findMember(loginRequest.getUserId());
        LoginResponse.LoginResponseBuilder loginResponse = LoginResponse.builder();
        loginResponse.isLogined(false);

        if(memberDto.getPassword() != null
                && memberDto.getPassword().equals(loginRequest.getPassword())) {

            HttpSession prevSession = httpServletRequest.getSession();
            prevSession.invalidate();
            log.info("prev session id: " + prevSession.getId());

            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute("emailAddress", memberDto.getEmailAddress());
            session.setAttribute("phoneNumber", memberDto.getPhoneNumber());
            session.setMaxInactiveInterval(1800); // Session이 30분동안 유지
            log.info("session id: " + session.getId());

            SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            loginResponse.sessionId(session.getId())
                    .loginDate(dtf.format(now.toDate()))
                    .emailAddress(memberDto.getEmailAddress())
                    .isLogined(true);
        }

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse.build());
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);  // Session이 없으면 null return
        if(session != null) {
            session.invalidate();
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                LoginResponse.builder()
                    .isLogined(false)
                    .build());
    }

    @GetMapping("/info")
    public ResponseEntity<?> info(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if(session == null)
            return ResponseEntity.status(HttpStatus.OK).body("Session is null...");

        /*
        RKeys keys = redissonClient.getKeys();
        Iterable<String> allKeys = keys.getKeys();
        for (String key: allKeys)
            log.info("... " + key);
        RMapCache<String, String> rMapCache = redissonClient.getMapCache(cacheName + sessionId);
        System.out.println("phone " + rMapCache.get("maxInactiveInterval"));
        System.out.println(rMapCache.getName());
        */

        Map<Object, Object> returnMap = new HashMap<>();
        returnMap.put("sessionId", session.getId());

        Set<Map.Entry<Object, Object>> entrySet = redissonClient
                .getMap(cacheName + session.getId(), StringCodec.INSTANCE)
                .readAllEntrySet();
        Iterator<Map.Entry<Object, Object>> iterator = entrySet.iterator();

        while(iterator.hasNext()) {
            Map.Entry<Object, Object> entry = iterator.next();
            if(((String)entry.getKey()).startsWith("sessionAttr"))
                returnMap.put(((String) entry.getKey()).split(":")[1], entry.getValue());
        }

        return ResponseEntity.status(HttpStatus.OK).body(returnMap);
    }

}
