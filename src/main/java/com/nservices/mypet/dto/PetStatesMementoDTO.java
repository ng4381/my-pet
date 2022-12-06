package com.nservices.mypet.dto;

import com.nservices.mypet.model.Pet;
import com.nservices.mypet.model.PetState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PetStatesMementoDTO {
    private Long pet_id;
    private List<PetState> petStates;
    private LocalDateTime dateTimeStamp;
}
