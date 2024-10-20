package com.example.critter.controller;

import com.example.critter.dto.responseDto.CritterResponseDto;
import com.example.critter.entity.Critter;
import com.example.critter.mapper.Mappers;
import com.example.critter.service.CritterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/critters")
@RequiredArgsConstructor
public class CritterController {
    private final CritterService critterService;
    private final Mappers mapper;

    @PostMapping
    public ResponseEntity<CritterResponseDto> createCritter(@RequestBody CritterResponseDto critterResponseDto) {
        Critter critter = mapper.convertResponseDtoToCritter(critterResponseDto);
        Critter savedCritter = critterService.createCritter(critter);
        CritterResponseDto responseDto = mapper.convertToCritterResponseDto(savedCritter);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<CritterResponseDto>> getAllCritters() {
        List<Critter> critters = critterService.getAllCritters();
        List<CritterResponseDto> responseDtos = critters.stream()
                .map(mapper::convertToCritterResponseDto)
                .toList();
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CritterResponseDto> getCritterById(@PathVariable UUID id) {
        Critter critter = critterService.getCritterById(id);
        CritterResponseDto responseDto = mapper.convertToCritterResponseDto(critter);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CritterResponseDto> updateCritter(@PathVariable UUID id, @RequestBody CritterResponseDto critterResponseDto) {
        Critter updatedCritter = mapper.convertResponseDtoToCritter(critterResponseDto);
        updatedCritter = critterService.updateCritter(id, updatedCritter);
        CritterResponseDto responseDto = mapper.convertToCritterResponseDto(updatedCritter);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCritter(@PathVariable UUID id) {
        critterService.deleteCritter(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/mood")
    public ResponseEntity<Void> changeMood(@PathVariable UUID id) {
        critterService.changeMood(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/power")
    public ResponseEntity<Void> alterPowerLevel(@PathVariable UUID id, @RequestBody Integer change) {
        if (change == null) {
            return ResponseEntity.badRequest().build();
        }
        critterService.alterPowerLevel(id, change);
        return ResponseEntity.ok().build();
    }
}
