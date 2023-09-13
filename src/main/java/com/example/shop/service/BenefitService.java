package com.example.shop.service;

import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class BenefitService {

    @Observed(name = "user.name",
            contextualName = "getTotalPoint",
            lowCardinalityKeyValues = {"userType", "userType2"})
    public int getTotalPoint(String userId) {
        final Random random = new Random();
        try {
            Thread.sleep(random.nextLong(200L));
        }
        catch(InterruptedException e) {
            throw new RuntimeException(e);
        }

        return getRandomPoint(100, 10000);
    }

    public int getRandomPoint(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
