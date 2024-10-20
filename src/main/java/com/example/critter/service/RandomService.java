package com.example.critter.service;

import com.example.critter.enums.CritterType;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomService {
    public CritterType getRandomCritterType() {
        CritterType[] types = CritterType.values();
        Random random = new Random();
        return types[random.nextInt(types.length)];
    }

    public int getRandomPowerLevel() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }
}
