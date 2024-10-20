package com.example.critter.entity;

import com.example.critter.enums.CritterMood;
import com.example.critter.enums.CritterSize;
import com.example.critter.enums.CritterType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "critters")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Critter {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CritterType type;

    @Column(nullable = false)
    private int powerLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CritterSize size;

    @Column(nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CritterMood mood;
}
