package com.example.critter.service;

import com.example.critter.entity.Critter;
import com.example.critter.enums.CritterMood;
import com.example.critter.enums.CritterType;
import com.example.critter.exceptions.ResourceNotFoundException;
import com.example.critter.repository.CritterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CritterService {
    private final CritterRepository critterRepository;
    private final RandomService randomService;
    private final List<Critter> critters = new ArrayList<>();

    public Critter createCritter(Critter critter) {
        CritterType randomType = randomService.getRandomCritterType();

        int randomPowerLevel = randomService.getRandomPowerLevel();

        critter.setType(randomType);
        critter.setPowerLevel(randomPowerLevel);

        return critterRepository.save(critter);
    }

    public List<Critter> getAllCritters() {
        return critterRepository.findAll();
    }

    public Critter getCritterById(UUID id) {
        return critterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Critter not found with id " + id));
    }

    public Critter updateCritter(UUID id, Critter updatedCritter) {
        Critter existingCritter = getCritterById(id);
        existingCritter.setName(updatedCritter.getName());
        existingCritter.setType(updatedCritter.getType());
        existingCritter.setPowerLevel(updatedCritter.getPowerLevel());
        existingCritter.setSize(updatedCritter.getSize());
        existingCritter.setAge(updatedCritter.getAge());
        existingCritter.setMood(updatedCritter.getMood());
        return critterRepository.save(existingCritter);
    }

    public void deleteCritter(UUID id) {
        Critter critter = getCritterById(id);
        critterRepository.delete(critter);
    }

    public void changeMood(UUID id) {
        Critter critter = critterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Critter with id " + id + " not found."));

        int powerLevel = critter.getPowerLevel();
        int age = critter.getAge();

        CritterMood newMood;

        if (powerLevel > 80 && age < 10) {
            newMood = CritterMood.HAPPY;
        } else if (powerLevel < 30 && age > 25) {
            newMood = CritterMood.SAD;
        } else if (age % 2 == 0) {
            newMood = CritterMood.ANGRY;
        } else {
            CritterMood[] moods = CritterMood.values();
            newMood = moods[new Random().nextInt(moods.length)];
        }

        critter.setMood(newMood);
        critterRepository.save(critter);
    }

    public void alterPowerLevel(UUID id, int change) {
        Critter critter = critterRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Critter with id " + id + " not found.")
        );

        critter.setPowerLevel(Math.max(0, critter.getPowerLevel() + change));
        critterRepository.save(critter);
    }
}
