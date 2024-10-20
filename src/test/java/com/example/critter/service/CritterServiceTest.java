package com.example.critter.service;

import com.example.critter.entity.Critter;
import com.example.critter.enums.CritterMood;
import com.example.critter.enums.CritterType;
import com.example.critter.repository.CritterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class CritterServiceTest {

    @Mock
    private CritterRepository critterRepository;

    @Mock
    private RandomService randomService;

    @InjectMocks
    private CritterService critterService;

    private Critter testCritter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testCritter = new Critter();
        testCritter.setId(UUID.randomUUID());
        testCritter.setPowerLevel(80);
        testCritter.setAge(5);
        testCritter.setType(CritterType.DRAGON);
        testCritter.setName("Test Critter");
    }

    @Test
    void testCreateCritter() {
        CritterType randomType = CritterType.DRAGON;
        int randomPowerLevel = 50;

        when(randomService.getRandomCritterType()).thenReturn(randomType);
        when(randomService.getRandomPowerLevel()).thenReturn(randomPowerLevel);

        when(critterRepository.save(any(Critter.class))).thenReturn(testCritter);

        Critter createdCritter = critterService.createCritter(testCritter);

        assertEquals(randomType, createdCritter.getType());
        assertEquals(randomPowerLevel, createdCritter.getPowerLevel());

        verify(critterRepository, times(1)).save(testCritter);
    }

    @Test
    void testChangeMood_HappyMood() {
        testCritter.setPowerLevel(85);
        testCritter.setAge(5);

        when(critterRepository.findById(testCritter.getId())).thenReturn(Optional.of(testCritter));

        critterService.changeMood(testCritter.getId());

        assertEquals(CritterMood.HAPPY, testCritter.getMood());

        verify(critterRepository, times(1)).save(testCritter);
    }

    @Test
    void testChangeMood_SadMood() {
        testCritter.setPowerLevel(25);
        testCritter.setAge(30);

        when(critterRepository.findById(any(UUID.class))).thenReturn(Optional.of(testCritter));

        critterService.changeMood(testCritter.getId());

        assertEquals(CritterMood.SAD, testCritter.getMood());
        verify(critterRepository, times(1)).save(testCritter);
    }

    @Test
    void testChangeMood_AngryMood() {
        testCritter.setAge(12);

        when(critterRepository.findById(any(UUID.class))).thenReturn(Optional.of(testCritter));

        critterService.changeMood(testCritter.getId());

        assertEquals(CritterMood.ANGRY, testCritter.getMood());
        verify(critterRepository, times(1)).save(testCritter);
    }

    @Test
    void testChangeMood_RandomMood() {
        testCritter.setPowerLevel(50);
        testCritter.setAge(7);  

        when(critterRepository.findById(any(UUID.class))).thenReturn(Optional.of(testCritter));

        critterService.changeMood(testCritter.getId());

        CritterMood currentMood = testCritter.getMood();
        assertTrue(currentMood == CritterMood.HAPPY || currentMood == CritterMood.ANGRY || currentMood == CritterMood.SAD);
        verify(critterRepository, times(1)).save(testCritter);
    }

    @Test
    void testChangeMood_CritterNotFound() {
        when(critterRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        UUID critterId = UUID.randomUUID();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            critterService.changeMood(critterId);
        });

        assertEquals("Critter with id " + critterId + " not found.", exception.getMessage());
    }

    @Test
    void testAlterPowerLevel_IncreasePowerLevel() {
        testCritter.setPowerLevel(80);

        when(critterRepository.findById(testCritter.getId())).thenReturn(Optional.of(testCritter));

        critterService.alterPowerLevel(testCritter.getId(), 20); 

        assertEquals(100, testCritter.getPowerLevel()); 
        verify(critterRepository, times(1)).save(testCritter); 
    }


    @Test
    void testAlterPowerLevel_DecreasePowerLevel() {
        testCritter.setPowerLevel(50);
        when(critterRepository.findById(testCritter.getId())).thenReturn(Optional.of(testCritter));

        critterService.alterPowerLevel(testCritter.getId(), -30);  

        assertEquals(20, testCritter.getPowerLevel()); 
        verify(critterRepository, times(1)).save(testCritter);
    }

    @Test
    void testAlterPowerLevel_NegativeResult() {
        testCritter.setPowerLevel(10);
        when(critterRepository.findById(testCritter.getId())).thenReturn(Optional.of(testCritter));

        critterService.alterPowerLevel(testCritter.getId(), -20); 

        assertEquals(0, testCritter.getPowerLevel());  
        verify(critterRepository, times(1)).save(testCritter);
    }

    @Test
    void testAlterPowerLevel_CritterNotFound() {
        when(critterRepository.findById(any(UUID.class))).thenReturn(Optional.empty()); 

        assertThrows(IllegalArgumentException.class, () -> {
            critterService.alterPowerLevel(UUID.randomUUID(), 10);  
        });
    }
}
