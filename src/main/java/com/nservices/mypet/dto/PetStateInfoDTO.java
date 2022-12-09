package com.nservices.mypet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PetStateInfoDTO {
    private String petName;
    private int PetAge;
    private String state;
    private long minutes;

}
