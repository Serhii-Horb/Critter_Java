package com.example.critter.dto.responseDto;

import com.example.critter.enums.CritterMood;
import com.example.critter.enums.CritterSize;
import com.example.critter.enums.CritterType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CritterResponseDto {

    private UUID id;

    @NotBlank(message = "Invalid name: Empty name")
    private String name;

    @NotBlank(message = "Invalid type: Empty type")
    private CritterType type;

    @NotBlank(message = "Invalid powerLevel: Empty powerLevel")
    private int powerLevel;

    @NotBlank(message = "Invalid size: Empty size")
    private CritterSize size;

    @NotBlank(message = "Invalid age: Empty age")
    private int age;

    @NotBlank(message = "Invalid mood: Empty mood")
    private CritterMood mood;
}
