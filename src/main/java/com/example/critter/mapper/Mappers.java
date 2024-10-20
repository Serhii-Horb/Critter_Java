package com.example.critter.mapper;

import com.example.critter.dto.responseDto.CritterResponseDto;
import com.example.critter.entity.Critter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mappers {
    private final ModelMapper modelMapper;

    public CritterResponseDto convertToCritterResponseDto(Critter critter) {
        return modelMapper.map(critter, CritterResponseDto.class);
    }

    public Critter convertResponseDtoToCritter(CritterResponseDto critterResponseDto) {
        return modelMapper.map(critterResponseDto, Critter.class);
    }

}
