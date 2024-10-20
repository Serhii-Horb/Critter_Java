package com.example.critter.repository;

import com.example.critter.entity.Critter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CritterRepository extends JpaRepository<Critter, UUID> {

}
