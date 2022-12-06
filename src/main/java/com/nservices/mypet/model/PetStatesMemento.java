package com.nservices.mypet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PetStatesMemento {
    private Pet pet;
    private List<PetState> petStates;
    private LocalDateTime dateTimeStamp;
}
