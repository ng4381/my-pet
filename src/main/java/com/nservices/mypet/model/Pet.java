package com.nservices.mypet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Pet {
    private String name;
    private int age;
    private LocalDateTime birthDate;
    private Owner owner;
}
