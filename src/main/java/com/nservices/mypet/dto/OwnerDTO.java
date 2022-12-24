package com.nservices.mypet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
public class OwnerDTO {
    private Long id;
    private String name;
    private String email;
    private int friendshipScore;
    private int personalScore;
    private int totalScore;
}
