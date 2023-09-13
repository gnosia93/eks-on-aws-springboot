package com.example.shop.controller;

import com.example.shop.service.BenefitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="/benefit")
@RestController
public class BenefitController {

    private final BenefitService benefitService;
    private final RestTemplate restTemplate;

    @ResponseBody
    @RequestMapping(value="/{memberId}", method= RequestMethod.GET)
    public ResponseEntity<?> getPoint(@PathVariable Integer memberId) {

        Map<String, Integer> responseMap = new HashMap<>();
        responseMap.put("point", benefitService.getRandomPoint(1, 1000));
        responseMap.put("accumulate", benefitService.getRandomPoint(1, 30000));

        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTotalPoint(@PathVariable("userId") int userId) {
        log.info("Got a request");

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("userId", userId);
        responseMap.put("accumulate", benefitService.getTotalPoint(userId));

        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }
}
