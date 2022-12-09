package com.nservices.mypet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistryInfoDTO {
    private String username;
    private String password;
    private String ownerName;
    private int ownerAge;
    private String email;
    private String petName;
}
