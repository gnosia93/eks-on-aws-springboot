package com.example.shop.controller;

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

    private final RestTemplate restTemplate;

    @ResponseBody
    @RequestMapping(value="/{memberId}", method= RequestMethod.GET)
    public ResponseEntity<?> getPoint(@PathVariable Integer memberId) {

        Map<String, Integer> responseMap = new HashMap<>();
        responseMap.put("point", getRandomPoint(1, 1000));
        responseMap.put("accumulate", getRandomPoint(1, 30000));

        return ResponseEntity.status(HttpStatus.OK).body(responseMap);
    }

    private int getRandomPoint(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
