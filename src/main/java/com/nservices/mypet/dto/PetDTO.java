package com.nservices.mypet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 {
    "id": "id",
    "name": "pet-1",
    "age": 0,
    "birthDate": "birthDate,
    "ownerId": 0,
    "ownerName": "Nikita",
    "ownerEmail": "nikitagoloveshko@gmail.com"
 }
 */
@Getter
@Setter
@AllArgsConstructor
public class PetDTO {
    private Long id;
    private String name;
    private int age;
    private LocalDateTime birthDate;
}
