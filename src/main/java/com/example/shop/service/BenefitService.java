package com.example.shop.service;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;


@Slf4j
@Service
public class BenefitService {

    @Observed(name = "user.name",
            contextualName = "getTotalPoint",
            lowCardinalityKeyValues = {"userType", "userType2"})
    public int getTotalPoint(int userId) {
        log.info("Getting user name for user with id <{}>", userId);

        final Random random = new Random();
        try {
            Thread.sleep(random.nextLong(200L));
        }
        catch(InterruptedException e) {
            throw new RuntimeException(e);
        }

        return getRandomPoint(100, 10000);
    }

    private int getRandomPoint(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
