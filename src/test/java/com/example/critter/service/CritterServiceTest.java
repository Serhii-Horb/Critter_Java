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

        // Мокаем репозиторий для возврата криттера после сохранения
        when(critterRepository.save(any(Critter.class))).thenReturn(testCritter);

        // Выполняем тестируемый метод
        Critter createdCritter = critterService.createCritter(testCritter);

        // Проверяем, что тип и уровень силы установлены корректно
        assertEquals(randomType, createdCritter.getType());
        assertEquals(randomPowerLevel, createdCritter.getPowerLevel());

        // Проверяем, что криттер сохранен в репозиторий
        verify(critterRepository, times(1)).save(testCritter);
    }

    @Test
    void testChangeMood_HappyMood() {
        // Устанавливаем параметры для настроения HAPPY: сила > 80, возраст < 10
        testCritter.setPowerLevel(85);
        testCritter.setAge(5);

        // Мокаем репозиторий, чтобы возвращать тестового криттера
        when(critterRepository.findById(testCritter.getId())).thenReturn(Optional.of(testCritter));

        // Выполняем метод
        critterService.changeMood(testCritter.getId());

        // Проверяем, что настроение изменилось на HAPPY
        assertEquals(CritterMood.HAPPY, testCritter.getMood());

        // Проверяем, что криттер был сохранен в репозиторий
        verify(critterRepository, times(1)).save(testCritter);
    }

    @Test
    void testChangeMood_SadMood() {
        // Устанавливаем условия для настроения SAD: сила < 30, возраст > 25
        testCritter.setPowerLevel(25);
        testCritter.setAge(30);

        when(critterRepository.findById(any(UUID.class))).thenReturn(Optional.of(testCritter));

        // Выполняем метод
        critterService.changeMood(testCritter.getId());

        // Проверяем, что настроение изменилось на SAD
        assertEquals(CritterMood.SAD, testCritter.getMood());
        verify(critterRepository, times(1)).save(testCritter);
    }

    @Test
    void testChangeMood_AngryMood() {
        // Устанавливаем условия для настроения ANGRY: возраст кратен 2
        testCritter.setAge(12);

        when(critterRepository.findById(any(UUID.class))).thenReturn(Optional.of(testCritter));

        // Выполняем метод
        critterService.changeMood(testCritter.getId());

        // Проверяем, что настроение изменилось на ANGRY
        assertEquals(CritterMood.ANGRY, testCritter.getMood());
        verify(critterRepository, times(1)).save(testCritter);
    }

    @Test
    void testChangeMood_RandomMood() {
        // Устанавливаем условия для случайного выбора настроения
        testCritter.setPowerLevel(50);
        testCritter.setAge(7);  // возраст нечетный, сила не попадает под другие условия

        when(critterRepository.findById(any(UUID.class))).thenReturn(Optional.of(testCritter));

        // Выполняем метод
        critterService.changeMood(testCritter.getId());

        // Проверяем, что настроение изменилось случайным образом, проверяя, что оно одно из допустимых
        CritterMood currentMood = testCritter.getMood();
        assertTrue(currentMood == CritterMood.HAPPY || currentMood == CritterMood.ANGRY || currentMood == CritterMood.SAD);
        verify(critterRepository, times(1)).save(testCritter);
    }

    @Test
    void testChangeMood_CritterNotFound() {
        // Когда криттер не найден
        when(critterRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        UUID critterId = UUID.randomUUID();

        // Проверяем, что выбрасывается исключение при вызове метода
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            critterService.changeMood(critterId);
        });

        // Проверяем сообщение исключения
        assertEquals("Critter with id " + critterId + " not found.", exception.getMessage());
    }

    @Test
    void testAlterPowerLevel_IncreasePowerLevel() {
        // Задайте начальный уровень силы криттера
        testCritter.setPowerLevel(80);

        // Мокаем метод findById для возврата testCritter
        when(critterRepository.findById(testCritter.getId())).thenReturn(Optional.of(testCritter));

        // Выполняем метод
        critterService.alterPowerLevel(testCritter.getId(), 20); // Увеличиваем уровень на 20

        // Проверяем, что уровень стал 100
        assertEquals(100, testCritter.getPowerLevel()); // Ожидаем, что уровень станет 100
        verify(critterRepository, times(1)).save(testCritter); // Проверяем, что save был вызван
    }


    @Test
    void testAlterPowerLevel_DecreasePowerLevel() {
        testCritter.setPowerLevel(50);
        when(critterRepository.findById(testCritter.getId())).thenReturn(Optional.of(testCritter));

        critterService.alterPowerLevel(testCritter.getId(), -30);  // Уменьшаем уровень на 30

        assertEquals(20, testCritter.getPowerLevel());  // Ожидаем, что уровень станет 20
        verify(critterRepository, times(1)).save(testCritter);
    }

    @Test
    void testAlterPowerLevel_NegativeResult() {
        testCritter.setPowerLevel(10);
        when(critterRepository.findById(testCritter.getId())).thenReturn(Optional.of(testCritter));

        critterService.alterPowerLevel(testCritter.getId(), -20);  // Уменьшаем уровень на 20

        assertEquals(0, testCritter.getPowerLevel());  // Убедитесь, что уровень не стал отрицательным
        verify(critterRepository, times(1)).save(testCritter);
    }

    @Test
    void testAlterPowerLevel_CritterNotFound() {
        when(critterRepository.findById(any(UUID.class))).thenReturn(Optional.empty());  // Возвращаем пустое значение

        assertThrows(IllegalArgumentException.class, () -> {
            critterService.alterPowerLevel(UUID.randomUUID(), 10);  // Проверяем на отсутствие криттера
        });
    }
}