package com.nservices.mypet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Owner {
    private String name;
    private String email;
}
