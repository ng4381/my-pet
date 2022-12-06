package com.nservices.mypet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OwnerDTO {
    private Long id;
    private String name;
    private String email;
}
